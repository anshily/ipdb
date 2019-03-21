package io.github.anshily.ipdb.core;

import com.gxchain.client.GXChainClient;
import io.github.anshily.ipdb.core.base.Constants;
import io.ipfs.api.IPFS;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CoreApplication {

	@Bean(name = "gxClient")
	public GXChainClient gxChainClient(){
		String activePrivateKey = "5KCuWPtZ1jTMNP9G7vETiNJdesA27RzWHmfmXZrkmibfM3WhUJ2";
		String accountIdOrName = Constants.EXAMPLE_ACCOUNT;
		String entryPoint = "http://ttq.tiantianquan.xyz:28090";
		GXChainClient client = new GXChainClient(activePrivateKey, accountIdOrName, entryPoint);
		return client;
	}

//	@Bean(name = "ipfsClient")
//	public IPFS ipfs(){
//		IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
//		return ipfs;
//	}

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

}
