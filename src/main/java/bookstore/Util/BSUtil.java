package bookstore.Util;

import bookstore.Entity.User;
import bookstore.Repository.UserRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class BSUtil {
    private final UserRepository userRepository;
    private  final Cloudinary cloudinary;
    public BSUtil(UserRepository userRepository, Cloudinary cloudinary) {
        this.userRepository = userRepository;
        this.cloudinary = cloudinary;
    }

    public User getCurrentUserLogin(){
        String currentUserStr = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmailIgnoreCase(currentUserStr);
    }
    public String uploadImageBase64(String rawBase64) throws IOException {
        if (rawBase64 == null || rawBase64.isEmpty()) return null;

        // Thêm tiền tố để tạo thành chuỗi base64 hợp lệ
        String base64Image = "data:image/png;base64," + rawBase64;

        Map uploadResult = cloudinary.uploader().upload(base64Image, ObjectUtils.emptyMap());

        return uploadResult.get("secure_url").toString();
    }

}
