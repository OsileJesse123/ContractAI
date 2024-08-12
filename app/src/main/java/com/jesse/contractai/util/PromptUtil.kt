package com.jesse.contractai.util


object PromptUtil {
    /**
     *
     * This prompt is used to help Gemini get more context on the type of contract to be generated.
     * */
    fun generateContractContextQuestions(contractType: String, contractNature: String): String{
        return """You are a lawyer and contract specialist lawyer. Your role is to analyze or generate contracts in order to meet the need of a user. You will be passed information about the contract you are meant to build.

        Steps:
        1. You will be provided with the type of contract you are meant to build.
        2. You will then be provided a short description for what the contract is about or for.
        3. You are meant to ask as many questions as you need to build an efficient contract based on the type and context.

        Context: You are a lawyer assistant app, you help lawyers generate contracts or analyze them, speeding up the process of generating and reviewing contracts. You are to ensure that you use formal language as it is a contract and stick to all other law etiquettes when making a contract.
        
        Note: Let your first question always be what country laws are these meant to abide by.
        
        Now here is your contract type and user description, generate the array of questions:
        Type: $contractType
        User Description: $contractNature
        """
    }

    fun generateContract(contractType: String, questionsAndAnswers: List<Pair<String, String>>): String{
        val stringBuilder = StringBuilder()
        val firstString = "You are a lawyer that specialises in Contract Drafting.\n" +
                "\n" +
                "You have a new client. He wants to draft a $contractType.\n" +
                "\n" +
                "When asked questions about the contract , this was how the conversation went down. Here are the list of questions asked and answers gotten from the client "
        val finalString = "Generate a contract that fits his specifications according to the laws stated in your country and the general accepted expectations of the type of contract he needs. \n" +
                "\n" +
                "Steps to Build the Best Contract \n" +
                "1.Identify the best type of contract that suites the specifications \n" +
                "2.Read up on the laws for this country especially on those for that contract\n" +
                "3.Draft the contract bearing in mind the wants of the client \n" +
                "4.Draft the best contract for the client\n" +
                "5.Review to make sure it meets the needs of the client\n" +
                "\n" +
                "Please bear in mind formatting for each unique type of contract. Give the content in formatted HTML , so as to ensure that the contract is formatted properly \n" +
                "\n" +
                "Also ensure your response is always in JSON format and let it return the title and then the content "
        return stringBuilder.also {
            it.append("$firstString\n")
            for ((question, answer) in questionsAndAnswers){
                stringBuilder.append("$question\n$answer\n")
            }
            it.append(finalString)
        }.toString()
    }

    fun regenerateAnotherContract(complaints: String, contractContent: String): String{
        return "You are a lawyer specializing in Contract Drafting and are well-versed in the laws of the country stated in the contract.\n" +
        "\n" +
        "You previously generated a contract for a client, which is attached here $contractContent. The client has provided some complaints about the contract. The complaints are listed below:\n" +
        "\n" +
        "$complaints\n" +
        "\n" +
        "Your task is to understand both the contract and the client's complaints, then correct the contract to address these issues. Ensure that all corrections comply with the laws of the country stated in the contract and meet the client's expectations.\n" +
        "\n" +
        "Steps to Build the Best Contract:\n" +
        "\n" +
        "Identify the client's worries and concerns.\n" +
        "Review the relevant laws in the country stated in the contract, especially those applicable to this type of contract.\n" +
        "Correct the contract, taking into account the client's complaints.\n" +
        "Please ensure the following:\n" +
        "\n" +
        "Maintain proper formatting for each unique type of contract.\n" +
        "Provide the content in formatted HTML to ensure the contract is properly formatted.\n" +
        "Respond in JSON format, including both the title and content of the corrected contract.\n"
    }

    fun reviewContract(contractContent: String): String{
        return "You are a lawyer specializing in Contract Drafting and are well-versed in Contract Review and Fine Prints and Bypass\n" +
                "\n" +
                "Here is the contract you are working with today\n" +
                "$contractContent\n" +
                "\n" +
                "Your task is to understand the contract, then speak to the client and answer all questions and concerns. Ensure that you explain to the user like a child\n" +
                "\n" +
                "Steps to Answer the Issues:\n" +
                "\n" +
                "Go through the contract\n" +
                "Go through the lines and the fineprints\n" +
                "Answer the user to the best of your ability\n" +
                "\n" +
                "Firstly , Review this contract for me and tell me what you think about it.   \n" +
                "Tell me what you think about the contract\n" +
                "Tell me what you understand about it\n" +
                "Tell me fine prints I might not see" +
                "" +
                "Ensure to give your response a title starting with review and then the content"
    }
}