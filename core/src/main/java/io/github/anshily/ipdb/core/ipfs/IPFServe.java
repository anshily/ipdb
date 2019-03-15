package io.github.anshily.ipdb.core.ipfs;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class IPFServe {

    @Autowired
    @Qualifier("ipfsClient")
    public IPFS ipfs;

    public String add(String data) throws IOException {
        NamedStreamable.ByteArrayWrapper fileWrapper = new NamedStreamable.ByteArrayWrapper(data.getBytes());
        MerkleNode addResult = ipfs.add(fileWrapper).get(0);
        return addResult.hash.toString();
    }

    public String cat(String hash) throws IOException {
        byte[] data = ipfs.cat(Multihash.fromBase58(hash));
        return new String(data);
    }
}
