package org.kang.assignment.scheduler;

import lombok.RequiredArgsConstructor;
import org.kang.assignment.service.scheduler.SettlementSchedulerService;
import org.kang.assignment.util.SettlementParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

@RequiredArgsConstructor
@Component
public class SettlementScheduler {

    private static final Logger logger = LoggerFactory.getLogger(SettlementScheduler.class);

    // FIXME: 개발 완료 시 자정마다 동작하도록 변경 필요
    private static final String CRON_EXP = "0/30 * * * * ?";

    private final SettlementSchedulerService settlementSchedulerService;

    @Value("${target.dir}")
    private String targetDir;

    @Value("${target.file}")
    private String targetFile;

    @Scheduled(cron = CRON_EXP)
    public void fileToDbScheduler() {
        File file = new File(targetDir, targetFile);
        if (!file.canRead()) {
            throw new RuntimeException("can not read file");
        }

        String ext = getExtension(file.getName());
        switch (ext) {
            case "csv":
                settlementSchedulerService.saveAllToDb(SettlementParser.csvToSettlement(file));
                break;
            case "txt":
                settlementSchedulerService.saveAllToDb(SettlementParser.txtToSettlement(file));
                break;
            default:
                logger.error("unsupported file type ...");
        }
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
