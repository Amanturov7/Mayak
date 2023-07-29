//package kg.irregationsystem.accounting.Controller;
//
//import kg.irregationsystem.accounting.Service.UserService;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Objects;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    private final UserService userService;
//
//    public AuthController(UserService service) {
//        this.userService = service;
//    }
//
//    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody kg.irregationsystem.accounting.Model.User getAuthUser() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth == null) {
//            return null;
//        }
//        Object principal = auth.getPrincipal();
//        User user = (principal instanceof User) ? (User) principal : null;
//        return Objects.nonNull(user) ? this.userService.getByLogin(user.getUsername()) : null;
//    }
//
//}


package kg.center.mayak.controllers;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import kg.center.mayak.model.ERole;
import kg.center.mayak.model.Role;
import kg.center.mayak.model.User;
import kg.center.mayak.repository.RoleRepository;
import kg.center.mayak.repository.UserRepository;
import kg.center.mayak.service.UserDetailsImpl;
import kg.center.mayak.configs.jwt.JwtUtils;
import kg.center.mayak.RequestResponse.JwtResponse;
import kg.center.mayak.RequestResponse.LoginRequest;
import kg.center.mayak.RequestResponse.msgBodyResponse;
import kg.center.mayak.RequestResponse.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRespository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @CrossOrigin(origins = "*")
    @PostMapping("/api/signin")
    public ResponseEntity<?> authUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/api/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {

        if (userRespository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new msgBodyResponse("Error: Username is exist"));
        }

        if (userRespository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new msgBodyResponse("Error: Email is exist"));
        }

        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                signupRequest.getFirstName(),
                signupRequest.getAge(),
                signupRequest.getLastName(),
                passwordEncoder.encode(signupRequest.getPassword()));

        Set<String> reqRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (reqRoles == null) {
            Role caRole = roleRepository
                    .findByName(ERole.ROLE_CA)
                    .orElseThrow(() -> new RuntimeException("Error, Role CA is not found"));
            roles.add(caRole);
        } else {
            reqRoles.forEach(r -> {
                switch (r) {
                    case "ruvh":
                        Role ruvhRole = roleRepository
                                .findByName(ERole.ROLE_RUVH)
                                .orElseThrow(() -> new RuntimeException("Error, Role Ruvh is not found"));
                        roles.add(ruvhRole);

                        break;
                    case "region":
                        Role regionRole = roleRepository
                                .findByName(ERole.ROLE_REGION)
                                .orElseThrow(() -> new RuntimeException("Error, Role Region is not found"));
                        roles.add(regionRole);

                        break;

                    default:
                        regionRole = roleRepository
                                .findByName(ERole.ROLE_REGION)
                                .orElseThrow(() -> new RuntimeException("Error, Role Region is not found"));
                        roles.add(regionRole);
                }
            });
        }
        user.setRoles(roles);
        userRespository.save(user);
        return ResponseEntity.ok(new msgBodyResponse("User CREATED"));
    }
}
