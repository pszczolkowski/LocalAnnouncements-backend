package pl.weeia.localannouncements.entity.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import pl.weeia.localannouncements.entity.util.JSR310DateConverters.DateToLocalDateTimeConverter;
import pl.weeia.localannouncements.entity.util.JSR310DateConverters.DateToZonedDateTimeConverter;
import pl.weeia.localannouncements.entity.util.JSR310DateConverters.LocalDateTimeToDateConverter;
import pl.weeia.localannouncements.entity.util.JSR310DateConverters.ZonedDateTimeToDateConverter;

public final class JSR310PersistenceConverters {

    private JSR310PersistenceConverters() {}

    @Converter(autoApply = true)
    public static class LocalDateConverter implements AttributeConverter<LocalDate, java.sql.Date> {

        @Override
        public java.sql.Date convertToDatabaseColumn(LocalDate date) {
            return date == null ? null : java.sql.Date.valueOf(date);
        }

        @Override
        public LocalDate convertToEntityAttribute(java.sql.Date date) {
            return date == null ? null : date.toLocalDate();
        }
    }

    @Converter(autoApply = true)
    public static class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, Date> {

        @Override
        public Date convertToDatabaseColumn(ZonedDateTime zonedDateTime) {
            return ZonedDateTimeToDateConverter.INSTANCE.convert(zonedDateTime);
        }

        @Override
        public ZonedDateTime convertToEntityAttribute(Date date) {
            return DateToZonedDateTimeConverter.INSTANCE.convert(date);
        }
    }

    @Converter(autoApply = true)
    public static class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Date> {

        @Override
        public Date convertToDatabaseColumn(LocalDateTime localDateTime) {
            return LocalDateTimeToDateConverter.INSTANCE.convert(localDateTime);
        }

        @Override
        public LocalDateTime convertToEntityAttribute(Date date) {
            return DateToLocalDateTimeConverter.INSTANCE.convert(date);
        }
    }
    
    @Converter(autoApply = true)
    public static class DurationConverter implements AttributeConverter<Duration, Integer> {

        @Override
        public Integer convertToDatabaseColumn(Duration duration) {
            return duration == null ? null : (int) duration.getSeconds();
        }

        @Override
        public Duration convertToEntityAttribute(Integer integer) {
            return Duration.ofSeconds(integer);
        }
    }
}
