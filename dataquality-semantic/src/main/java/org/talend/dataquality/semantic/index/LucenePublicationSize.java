package org.talend.dataquality.semantic.index;

import static org.talend.dataquality.semantic.api.CategoryRegistryManager.DICTIONARY_SUBFOLDER_NAME;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.Version;
import org.talend.dataquality.semantic.api.CategoryRegistryManager;
import org.talend.dataquality.semantic.api.CustomDictionaryHolder;
import org.talend.dataquality.semantic.api.DictionaryUtils;
import org.talend.dataquality.semantic.index.DictionarySearcher;
import org.talend.dataquality.semantic.model.DQCategory;
import org.talend.dataquality.semantic.model.DQDocument;

public class LucenePublicationSize {

    private static String[] CATEGORY_NAMES = new String[] { "CIVILITY", "AIRPORT_CODE", "CITY", "CONTINENT", "CONTINENT_CODE",
            "COUNTRY", "ANSWER", "COUNTRY_CODE_ISO2", "COUNTRY_CODE_ISO3", "CURRENCY_NAME", "ANIMAL", "HR_DEPARTMENT",
            "CURRENCY_CODE", "FIRST_NAME", "AIRPORT", "MONTH", "JOB_TITLE", "WEEKDAY", "STREET_TYPE", "MUSEUM", "ORGANIZATION",
            "US_COUNTY", "LAST_NAME", "GENDER", "COMPANY", "BEVERAGE", "SECTOR", "FR_COMMUNE", "FR_DEPARTEMENT", "FR_REGION",
            "INDUSTRY_GROUP", "FR_REGION_LEGACY", "LANGUAGE_CODE_ISO2", "LANGUAGE", "LANGUAGE_CODE_ISO3", "MEASURE_UNIT",
            "CA_PROVINCE_TERRITORY", "MX_ESTADO", "MX_ESTADO_CODE", "INDUSTRY", "CA_PROVINCE_TERRITORY_CODE", "EN_WEEKDAY",
            "EN_MONTH", "EN_MONTH_ABBREV", "US_STATE", "US_STATE_CODE" };

    public static void main(String[] args) throws IOException, InterruptedException {
        LucenePublicationSize appli = new LucenePublicationSize();

        String sourcePath = LucenePublicationSize.class.getResource("/").getPath() + DICTIONARY_SUBFOLDER_NAME;
        File destFolder = new File(sourcePath + "_clone");
        if (destFolder.exists()) {
            FileUtils.deleteDirectory(new File(sourcePath + "_clone/t_default/prod"));
        }

        CategoryRegistryManager.setLocalRegistryPath(sourcePath + "_clone");

        for (int i = 0; i < 9; i++) {
            appli.updateCategoryDescription(CATEGORY_NAMES[i]);
            System.out.print("publication #" + i + " done");
            System.out.println();
        }
    }

    private void updateCategoryDescription(String categoryName) {
        CategoryRegistryManager crm = CategoryRegistryManager.getInstance();
        DQCategory meta = crm.getCategoryMetadataByName(categoryName);
        DQCategory clone = SerializationUtils.clone(meta);
        clone.setDescription("description of " + categoryName);
        CategoryRegistryManager.getInstance().getCustomDictionaryHolder().updateCategory(clone);
    }

}
