package hello.upload.domain;

import lombok.Data;

import java.util.List;

@Data
public class Item {

    private Long id;
    private String itemName;
    private UploadFile attachFile; // 고객이 업로드한 파일명
    private List<UploadFile> imageFiles; // 서버 내부에서 관리하는 파일명

}
