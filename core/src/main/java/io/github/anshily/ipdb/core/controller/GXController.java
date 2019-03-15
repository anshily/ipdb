package io.github.anshily.ipdb.core.controller;

import io.github.anshily.ipdb.core.gxc.GXCserve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gxc")
public class GXController {
    @Autowired
    GXCserve gxCserve;

    @GetMapping("/call")
    public String callContract(){
        return gxCserve.callContract();
    }

    @GetMapping("/idx1")
    public String idx1(int idx1){
        return gxCserve.getByIndex1(idx1);
    }
}
