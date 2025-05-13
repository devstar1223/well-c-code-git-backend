package com.wccg.well_c_code_git_backend.global.util.nickname;

import com.wccg.well_c_code_git_backend.global.util.random.RandomNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class NicknameGenerator {

    private final NicknameCsvLoader nicknameCsvLoader;

    public String generate() {
        String trait = nicknameCsvLoader.getRandomTrait();
        String animal = nicknameCsvLoader.getRandomAnimal();
        int number = RandomNumberGenerator.generate(100, 1000);
        return trait + animal + number;
    }
}

