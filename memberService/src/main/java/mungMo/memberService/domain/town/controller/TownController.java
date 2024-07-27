package mungMo.memberService.domain.town.controller;


import jakarta.servlet.http.HttpServletRequest;
import mungMo.memberService.com.config.ResponseMessage;
import mungMo.memberService.domain.town.service.TownService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class TownController {
    private final TownService townService;

    public TownController(TownService townService) {
        this.townService = townService;
    }

    @PatchMapping("/registerTown")
    public ResponseEntity<?> registerTown(HttpServletRequest request, @RequestParam String area) {
        townService.register(area, Long.parseLong(request.getHeader("userId")));
        return ResponseEntity.ok(ResponseMessage.valueOfCode("Ok").getMessage());
    }
}
