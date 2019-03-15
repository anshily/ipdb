package io.github.anshily.ipdb.core.controller;

import io.github.anshily.ipdb.core.ipfs.IPFServe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/ipfs")
public class IPFSController {
    @Autowired
    IPFServe ipfServe;

    @GetMapping("/add")
    public String add(String data){
        String ret;
        try {
            ret = ipfServe.add(data);
        } catch (IOException e) {
            ret = "error";
            e.printStackTrace();
        }
        return ret;
    }

    @GetMapping("/cat")
    public String cat(String hash){
        String ret;
        try {
            ret = ipfServe.cat(hash);
        } catch (IOException e) {
            e.printStackTrace();
            ret = "error";
        }
        return ret;
    }
}
