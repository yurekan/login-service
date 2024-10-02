
# Spring Security with JWT - Documentation

## Table of Contents
1. [Security Filter Chain](#security-filter-chain)
2. [Intercepting JWT Token](#intercepting-jwt-token)
3. [Login Controller](#login-controller)
4. [Generating JWT](#generating-jwt)
5. [Return JWT from Login](#return-jwt-from-login)

---

## 1. Security Filter Chain

The `SecurityFilterChain` in Spring Security is responsible for configuring security at the HTTP level. When using JWT for authentication, the filter chain will include a JWT Authentication Filter to ensure every incoming request is validated for a valid token.

### Key Components:
- **CSRF**: Disabled for stateless applications using JWT.
- **Authorization**: Permits certain paths (e.g., `/api/auth/**`) and requires authentication for others.
- **Filter Addition**: A custom JWT Authentication Filter is added to intercept requests before they reach the main security filters.

```java
@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // You can customize this as needed
                .exceptionHandling((exception) ->
                        exception.authenticationEntryPoint(authEntryPoint)
                )
                .sessionManagement((session) ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authz ->
                        authz.requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> {}); // Configure basic auth as needed
        http.addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
```

---

## 2. Intercepting JWT Token

A **JWT Authentication Filter** is responsible for intercepting requests and extracting the JWT token from the `Authorization` header. It validates the token and sets the authentication details in the security context.

### Steps:
1. **Extract JWT from Header**: The filter looks for a `Bearer` token in the `Authorization` header.
2. **Validate the Token**: The token is validated to ensure its authenticity, issuer, and expiration.
3. **Set Authentication**: If valid, the authentication is set in the security context for the current request.

```java
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtGenerator tokenGenerator;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = getJwtFromRequest(request);
        if (StringUtils.hasText(token) && tokenGenerator.validateToken(token)){
            String username = tokenGenerator.getUsernameFromJWT(token);
            UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, userDetails.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
```

---

## 3. Login Controller

The **Login Controller** is responsible for handling login requests. It authenticates the user’s credentials and, if valid, generates and returns a JWT token.

### Flow:
1. **User Login Request**: User submits credentials (username and password) to the `/login` endpoint.
2. **Authenticate Credentials**: Spring Security’s `AuthenticationManager` authenticates the credentials.
3. **Generate JWT**: If the credentials are valid, a JWT is generated and returned.

```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication); // Generate JWT

        return ResponseEntity.ok(new JwtResponse(jwt)); // Return JWT to client
    }
}
```

---

## 4. Generating JWT

After successful authentication, a JWT token is generated using claims such as the user’s details, roles, and expiration time. The token is signed with a secret key to ensure it is tamper-proof.

### JWT Generation Process:
1. **Set Claims**: User details, roles, and expiration time are added to the token.
2. **Sign the Token**: The token is signed using the secret key with a hashing algorithm like `HS512`.
3. **Return the Token**: The generated token is returned to the caller.

```java
@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpiration;

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret) // Sign with secret key
                .compact();
    }
}
```

---

## 5. Return JWT from Login

After the JWT token is generated, it is returned as a part of the login response. The client then stores this token (e.g., in `localStorage`) and sends it in the `Authorization` header for future requests.

### JWT Response Example:
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2huZG9lIiwiaXNzIjoiYXV0aF9zZXJ2ZXIiLCJpYXQiOjE1Nzg2MjUxMjcsImV4cCI6MTU3ODYyODYyN30.Aa4LC5Wf0Fb4kBh..."
}
```

### Example Controller Response:
```java
return ResponseEntity.ok(new JwtResponse(jwt));
```

### Client-Side Usage:
The client includes the JWT in the `Authorization` header for authenticated requests:
```http
Authorization: Bearer <jwt_token>
```

---

## Summary of JWT Flow

1. **User logs in** → The user provides username and password.
2. **Server authenticates** → If credentials are valid, JWT is generated.
3. **JWT is returned** → JWT is sent to the client in the response.
4. **Client stores JWT** → JWT is stored on the client side (e.g., `localStorage`).
5. **Client sends JWT** → JWT is sent in the `Authorization` header for protected requests.
6. **Server validates JWT** → The server validates JWT on each request to determine user identity.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
