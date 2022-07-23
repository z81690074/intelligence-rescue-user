
package com.aojun.user.server.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;


@Configuration
public class KaptchaConfiguration {

	@Bean
	public DefaultKaptcha producer() {
		
		Properties properties = new Properties();
		properties.put(Constants.KAPTCHA_BORDER, "no");
		properties.put(Constants.KAPTCHA_NOISE_IMPL, "com.google.code.kaptcha.impl.NoNoise");
		properties.put(Constants.KAPTCHA_IMAGE_WIDTH, "100");
		properties.put(Constants.KAPTCHA_IMAGE_HEIGHT, "40");
		properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
		properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "5");
		properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "30");
		properties.put(Constants.KAPTCHA_BACKGROUND_CLR_FROM,"245,247,250");
		properties.put(Constants.KAPTCHA_BACKGROUND_CLR_TO,"245,247,250");
		
		Config config = new Config(properties);
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		defaultKaptcha.setConfig(config);


		return defaultKaptcha;
	}
}
