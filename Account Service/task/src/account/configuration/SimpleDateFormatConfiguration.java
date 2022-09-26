package account.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

@Configuration
public class SimpleDateFormatConfiguration {

    @Bean
    @Primary
    public SimpleDateFormat simpleDateFormat() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-yyyy", Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getDefault());
        return formatter;
    }

    @Bean
    public SimpleDateFormat fullDateFormat() {
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM-yyyy", Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getDefault());
        return formatter;
    }
}
