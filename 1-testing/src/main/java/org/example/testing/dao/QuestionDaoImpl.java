package org.example.testing.dao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.example.testing.component.LocaleProvider;
import org.example.testing.domain.AnswerVariant;
import org.example.testing.domain.QuestionWithAnswer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class QuestionDaoImpl implements QuestionDao {

    private static final char SPLIT_BY_SYMBOL = ';';
    private static final String SPLIT_ANSWERS_BY_SYMBOL = ",";

    private static final String FILE_READING_ERROR = "Error reading file";
    private static final String FILE_PARSING_ERROR = "Error parsing file";

    private final String testFile;
    private final LocaleProvider localeProvider;

    public QuestionDaoImpl(@Value("${settings.test-file}") String testFile,
                           LocaleProvider localeProvider) {
        this.testFile = testFile;
        this.localeProvider = localeProvider;
    }

    public List<QuestionWithAnswer> getQuestionsAndAnswers() {
        List<QuestionWithAnswer> questionWithAnswers = new ArrayList<>();

        List<String[]> lines = extractLinesFromFile();

        for (String[] line : lines) {
            String questionText = line[1];

            String[] answerVariantsArray = Arrays.copyOfRange(line, 2, line.length);
            List<AnswerVariant> answerVariants = extractAnswerVariants(answerVariantsArray);

            questionWithAnswers.add(new QuestionWithAnswer(questionText, answerVariants));
        }

        return questionWithAnswers;
    }

    private List<String[]> extractLinesFromFile() {

        try (InputStream is = getClass().getResourceAsStream(testFile)) {
            if (is != null) {
                CSVParser csvParser = new CSVParserBuilder().withSeparator(SPLIT_BY_SYMBOL).build();
                CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(is))
                        .withSkipLines(1)
                        .withCSVParser(csvParser)
                        .build();

                List<String[]> lines = csvReader.readAll();

                return extractLinesForCurrentLocale(lines);
            }
        } catch (IOException e) {
            log.error(FILE_READING_ERROR);
        } catch (CsvException e) {
            log.error(FILE_PARSING_ERROR);
        }

        return new ArrayList<>();
    }

    private List<String[]> extractLinesForCurrentLocale(List<String[]> lines) {

        return lines.stream()
                .filter(line -> line[0].equals(localeProvider.getLocale().getLanguage()))
                .collect(Collectors.toList());
    }

    private List<AnswerVariant> extractAnswerVariants(String[] answerVariantsArray) {
        List<AnswerVariant> answerVariants = new ArrayList<>(answerVariantsArray.length - 1);

        String rightAnswer = answerVariantsArray[0];

        String[] answers = splitAnswerVariants(answerVariantsArray[1]);

        for (String variant : answers) {
            boolean isCorrect = rightAnswer.equals(variant);

            AnswerVariant answerVariant = new AnswerVariant(variant, isCorrect);
            answerVariants.add(answerVariant);
        }

        return answerVariants;
    }

    private String[] splitAnswerVariants(String answerVariants) {

        return answerVariants.split(SPLIT_ANSWERS_BY_SYMBOL);
    }
}
