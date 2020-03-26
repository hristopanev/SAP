package com.sap.refapps.objectstore.service;

import java.io.InputStream;
import java.util.List;
import org.springframework.stereotype.Service;
import com.sap.refapps.objectstore.model.BlobFile;

@Service
public interface ObjectStoreService {

    public String uploadFile(byte[] bytes, String name, String contentType);

    public boolean deleteFile(String fileName);

    public InputStream getFile(String fileName);

    public List<BlobFile> listObjects();

    public boolean isBlobExist(String name);
}
