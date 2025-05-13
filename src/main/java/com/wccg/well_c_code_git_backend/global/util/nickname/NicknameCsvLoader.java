package com.wccg.well_c_code_git_backend.global.util.nickname;

import com.wccg.well_c_code_git_backend.global.util.random.RandomNumberGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class NicknameCsvLoader {

    private final List<String> traits = new ArrayList<>();
    private final List<String> animals = new ArrayList<>();

    @PostConstruct
    public void loadData() {
        try {
            ClassPathResource resource = new ClassPathResource("trait_animal_nickname.csv");
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                String line;
                boolean isFirst = true;
                while ((line = reader.readLine()) != null) {
                    if (isFirst) {
                        isFirst = false;
                        continue;
                    }
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        traits.add(parts[0].trim());
                        animals.add(parts[1].trim());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("trait_animal_nickname.csv 파싱 과정중 오류가 발생했습니다.");
        }
    }

    public String getRandomTrait() {
        return traits.get(RandomNumberGenerator.generate(0,traits.size()));
    }

    public String getRandomAnimal() {
        return animals.get(RandomNumberGenerator.generate(0,animals.size()));
    }
}
