package io.github.anshily.ipdb.core.gxc;

import com.google.gson.JsonObject;
import com.gxchain.client.GXChainClient;
import com.gxchain.client.domian.TransactionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class GXCserve {

    private static final Logger log = LoggerFactory.getLogger(GXCserve.class);

    @Autowired
    @Qualifier("gxClient")
    GXChainClient gxChainClient;

    public String callContract(){
        JsonObject param = new JsonObject();
        param.addProperty("i1", 1);
        param.addProperty("i2",2);
        param.addProperty("name","test sweet blockchain table add ：再见只是点头 寒暄也顾不上 大家都去饮一勺星光 愿星光照亮的最远方 能够闲下来补话家常 早安 孤独小美 格致别趣 六点起床第713天 迟到72天");
        TransactionResult transactionResult = gxChainClient.callContract("sweet-baas", "additem", param, null, true);
        String ret = transactionResult.getTransaction().toJsonString();
        log.info(ret);
        return ret;
    }

    public String getByIndex1(int idx1){
        JsonObject param = new JsonObject();
        param.addProperty("key", idx1);

        TransactionResult transactionResult = gxChainClient.callContract("sweet-baas", "getbyidx1", param, null, true);
        String ret = transactionResult.getTransaction().toJsonString();
        log.info(ret);
        return ret;
    }
}
