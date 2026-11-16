
package com.urlshortner;

import java.sql.SQLException;

public class UrlService {
    private UrlRepository repo = new UrlRepository();
    private long counter = 1;

    public String shortenUrl(String originalUrl) throws SQLException {
    String existingCode = repo.findByOriginalUrl(originalUrl);

    if (existingCode != null) {
        return existingCode;
    }

    String code;

    do {
        code = base62(counter);
        counter++;
    } while (repo.findByCode(code) != null);

    repo.save(code, originalUrl);
    return code;
}

    public String base62(long id) {
        String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String str = "";

        if (id == 0) return "0";

        while (id > 0) {
            int remainder = (int) (id % 62);
            str = chars.charAt(remainder) + str;
            id = id / 62;
        }

        return str;
    }

    public String getOriginalUrl(String code) throws SQLException {
        return repo.findByCode(code);
    }
}
