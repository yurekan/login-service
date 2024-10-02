
# Spring Security Components

## 1. UserDetailsService
- **Purpose**: To load user-specific data during authentication.
- **Role**: This interface is used to fetch user details from a data source (e.g., a database). When a user tries to log in, the username is passed to `UserDetailsService`, which loads a `UserDetails` object containing information like the username, password, and roles.
- **How it works**: Implement the `UserDetailsService` interface and override the `loadUserByUsername(String username)` method to fetch the user from your database.

    ```java
    @Service
    public class MyUserDetailsService implements UserDetailsService {
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            // Fetch user from database and convert to UserDetails
        }
    }
    ```

## 2. AuthenticationProvider
- **Purpose**: To validate the provided credentials.
- **Role**: An `AuthenticationProvider` is responsible for performing the actual authentication. It uses the information from `UserDetailsService` to check if the username and password are correct. If valid, it returns an `Authentication` object.
- **How it works**: You can implement your custom `AuthenticationProvider` or use predefined ones like `DaoAuthenticationProvider`.

    ```java
    public class CustomAuthenticationProvider implements AuthenticationProvider {
        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            // Authentication logic here
        }
    }
    ```

## 3. AuthenticationManager
- **Purpose**: To manage authentication by delegating to `AuthenticationProvider`.
- **Role**: The `AuthenticationManager` delegates the authentication process to one or more `AuthenticationProvider`s. If one of them successfully authenticates, it returns the corresponding `Authentication` object.
- **How it works**: Typically, `AuthenticationManager` has a list of `AuthenticationProvider`s to check for valid credentials.

    ```java
    @Autowired
    private AuthenticationManager authenticationManager;

    public void authenticateUser(String username, String password) {
        Authentication request = new UsernamePasswordAuthenticationToken(username, password);
        Authentication result = authenticationManager.authenticate(request);
    }
    ```

## 4. Authentication Filter
- **Purpose**: To intercept incoming requests and perform the authentication process.
- **Role**: The filter intercepts HTTP requests (like login requests) and extracts credentials (such as username and password) from the request. It then delegates the authentication process to the `AuthenticationManager`. If successful, the request is passed on to other filters; otherwise, an exception is thrown.
- **How it works**: Spring Security typically uses `UsernamePasswordAuthenticationFilter` to process login requests, but you can implement your own.

    ```java
    public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
                throws AuthenticationException {
            // Extract credentials and authenticate
        }
    }
    ```

## 5. SecurityContext
- **Purpose**: To hold the current user's security information (like the authentication token).
- **Role**: Once a user is authenticated, the `SecurityContext` stores the `Authentication` object. It can be accessed across the application to verify the user's identity and roles. Spring Security automatically stores this in the `SecurityContextHolder`.
- **How it works**: You can access the `SecurityContext` in your code to check the authentication status.

    ```java
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.isAuthenticated()) {
        // User is authenticated
    }
    ```

## How These Components Work Together
1. A request hits the **Authentication Filter**, which extracts credentials.
2. The credentials are passed to the **AuthenticationManager**.
3. The **AuthenticationManager** checks the credentials by delegating to an appropriate **AuthenticationProvider**.
4. The **AuthenticationProvider** uses **UserDetailsService** to load user data and validate the credentials.
5. If authentication is successful, the authenticated user information is stored in the **SecurityContext**.
6. Subsequent requests use the stored authentication token from the **SecurityContext** for authorization checks.
