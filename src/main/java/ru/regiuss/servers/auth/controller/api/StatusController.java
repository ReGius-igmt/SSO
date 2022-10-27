package ru.regiuss.servers.auth.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.regiuss.servers.auth.model.Status;

@Controller
@ResponseBody
public class StatusController {

    private static final long startDate = System.currentTimeMillis();

    @RequestMapping("/status")
    public Status status() {
        return new Status("Account", startDate);
    }
}
