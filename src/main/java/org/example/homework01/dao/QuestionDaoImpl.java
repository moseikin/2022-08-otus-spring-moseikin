package org.example.homework01.dao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.example.homework01.domain.AnswerVariant;
import org.example.homework01.domain.QuestionWithAnswer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class QuestionDaoImpl implements QuestionDao {

    private static final char SPLIT_BY_SYMBOL = ';';
    private static final String SPLIT_ANSWERS_BY_SYMBOL = ",";
    private static final String FILE_READING_ERROR = "Error reading file";
    private static final String FILE_PARSING_ERROR = "Error parsing file";


    private final String fileName;

    public QuestionDaoImpl(@Value("${test-file}") String fileName) {
        this.fileName = fileName;
    }

    public List<QuestionWithAnswer> getQuestionsAndAnswers() {
        List<QuestionWithAnswer> questionWithAnswers = new ArrayList<>();

        List<String[]> lines = extractLinesFromFile();

        for (String[] line : lines) {
            String questionText = line[0];

            String[] answerVariantsArray = Arrays.copyOfRange(line, 1, line.length);
            List<AnswerVariant> answerVariants = extractAnswerVariants(answerVariantsArray);

            questionWithAnswers.add(new QuestionWithAnswer(questionText, answerVariants));
        }

        return questionWithAnswers;
    }

    private List<String[]> extractLinesFromFile() {

        try (InputStream is = getClass().getResourceAsStream(fileName)) {
            if (is != null) {
                CSVParser csvParser = new CSVParserBuilder().withSeparator(SPLIT_BY_SYMBOL).build();
                CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(is))
                        .withSkipLines(1)
                        .withCSVParser(csvParser)
                        .build();

                return csvReader.readAll();
            }
        } catch (IOException e) {
            System.out.println(FILE_READING_ERROR);
        } catch (CsvException e) {
            System.out.println(FILE_PARSING_ERROR);
        }

        return new ArrayList<>();
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
