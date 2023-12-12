package com.stage.teamb.services.auth;


//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class LogoutService implements LogoutHandler {
//
//    private final UsersRepository usersRepository;
//
//    @Override
//    public void logout(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            Authentication authentication
//    ) {
//
//        final String authHeader = request.getHeader("Authorization");
//        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
//            log.warn(" not found jwt in logout");
//            return;
//        }
//            SecurityContextHolder.clearContext();
//    }
//
//
//
//}