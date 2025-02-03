package org.koreait.member.controllers;

import lombok.RequiredArgsConstructor;
import org.koreait.member.services.TempTokenService;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TokenController {

    private final TempTokenService tempTokenService;

}
