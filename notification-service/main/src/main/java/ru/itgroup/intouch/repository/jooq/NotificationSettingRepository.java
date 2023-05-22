package ru.itgroup.intouch.repository.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.TableField;
import org.springframework.stereotype.Repository;
import ru.itgroup.intouch.tables.records.NotificationSettingsRecord;

import static ru.itgroup.intouch.Tables.NOTIFICATION_SETTINGS;

@Repository
@RequiredArgsConstructor
public class NotificationSettingRepository {
    private final DSLContext dsl;

    public boolean isEnable(Long userId, TableField<NotificationSettingsRecord, Boolean> field) {
        return dsl.select(field)
                  .from(NOTIFICATION_SETTINGS)
                  .where(NOTIFICATION_SETTINGS.USER_ID.eq(userId))
                  .fetchOptional()
                  .map(record -> record.getValue(field))
                  .orElse(false);
    }
}
