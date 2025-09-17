WORKSTREAM ERP

@GetMapping("/current-user")
public ResponseEntity<?> getCurrentUser() {
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
String username = authentication.getName();
Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

    // Your logic here
    return ResponseEntity.ok("Current user: " + username);
}


{
"username": "admin",
"password": "admin12345",
"email": "admin@example.com"
}