package com.example.covid_19visualizer.ui.main;

public class Questions {

    public String mQuestions[] = {
            "Is this an emergency?",
            "How old are you?",
            "Are you experiencing any of these symptoms?",
            "Do any of these apply to you?",
            "In the last 14 days, have you traveled internationally?",
            "In the last 14 days, have you been in an area where COVID-19 is widespread?",
            "In the last 14 days, what is your exposure to others who are known to have COVID-19?",
            "Do you live in a care facility?",
            "Do you work in a medical facility?",
            "Which state are you in?"
            // Screening Test Questions
    };

    private String[][] mChoices = {
            //"Is this an emergency?" answers
            {"I'm experiencing at least one of these symptoms", "I do not have any of these symptoms", " ", " ", " ", " ", " ", " ", " ", " ", " ", "Next"},
            //"How old are you?" answers
            {"Under 18", "Between 18 and 64", "65 or older", " ", " ", " ", " ", " ", " ", " ", " ","Next"},
            //"Are you experiencing any of these symptoms?" answers
            {"Fever, chills, or sweating", "Difficulty breathing (not severe)", "New or worsening cough", "Sore throat", "Aching throughout the body",
                    "Vomiting or diarrhea", "None of the above", " ", " ", " ", " ", "Next"},
            //"Do any of these apply to you?" answers
            {"Moderate to severe asthma or chronic lung disease", "Cancer treatment or medicines causing immune suppression",
                    "Inherited immune system deficiencies or HIV", "Serious heart conditions, such as heart failure or prior heart attack",
                    "Diabetes with complications", "Kidney failure that needs dialysis", "Cirrhosis of the liver",
                    "Diseases or conditions that make it harder to cough", "Extreme obesity", "Pregnancy", "None of the above", "Next"},
            //"In the last 14 days, have you traveled internationally?" answers
            {"I have traveled internationally", "I have not traveled internationally", " ", " ", " ", " ", " ", " ", " ", " ", " ", "Next"},
            //"In the last 14 days, have you been in an area where COVID-19 is widespread?" answers
            {"I live in an area where COVID-19 is widespread", "I have visited an area where COVID-19 is widespread", "I don't know",
                    "None of the above", " ", " ", " ", " ", " ", " ", " ", "Next"},
            //"In the last 14 days, what is your exposure to others who are known to have COVID-19?" answers
            {"I live with someone who has COVID-19", "I've had close contact with someone who has COVID-19",
                    "I've been near someone who has COVID-19", "I've had no exposure", "I don't know", " ", " ", " ", " ", " ", " ", "Next"},
            //"Do you live in a care facility?" answers
            {"I live in a long-term care facility", "No, I don't live in a long-term care facility", " ", " ", " ", " ", " ", " ", " ", " ", " ", "Next"},
            //"Do you live in a medical facility?" answers
            {"I have worked in a hospital or other care facility in the past 14 days",
                    "I plan to work in a hospital or other care facility in the next 14 days",
                    "No, I don't work or plan to work in a care facility", " ", " ", " ", " ", " ", " ", " ", " ", "Next"},
    };

    private String[] mCorrectAnswers = {"Next", "Next", "Next", "Next", "Next", "Next", "Next", "Next", "Next", "Next", "Next"};

    public String getQuestion(int a) {
        String question = mQuestions[a];
        return question;
    };

    public String getChoice1(int a) {
        String choice1 = mChoices[a][0];
        return choice1;
    };

    public String getChoice2(int a) {
        String choice2 = mChoices[a][1];
        return choice2;
    };

    public String getChoice3(int a) {
        String choice3 = mChoices[a][2];
        return choice3;
    };

    public String getChoice4(int a) {
        String choice4 = mChoices[a][3];
        return choice4;
    };

    public String getChoice5(int a) {
        String choice5 = mChoices[a][4];
        return choice5;
    };

    public String getChoice6(int a) {
        String choice6 = mChoices[a][5];
        return choice6;
    };

    public String getChoice7(int a) {
        String choice7 = mChoices[a][6];
        return choice7;
    };

    public String getChoice8(int a) {
        String choice8 = mChoices[a][7];
        return choice8;
    };

    public String getChoice9(int a) {
        String choice9 = mChoices[a][8];
        return choice9;
    };

    public String getChoice10(int a) {
        String choice10 = mChoices[a][9];
        return choice10;
    };

    public String getChoice11(int a) {
        String choice11 = mChoices[a][10];
        return choice11;
    };

    public String getChoice12(int a) {
        String choice12 = mChoices[a][11];
        return choice12;
    };

    public String getCorrectAnswer(int a) {
        String answer = mCorrectAnswers[a];
        return answer;
    };
}
