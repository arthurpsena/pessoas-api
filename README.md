Teste de Qualificação Backend SRM
===================

A Empresa X é uma instituição financeira inovadora e próspera, especializada na concessão de empréstimos. Fundada há alguns anos por um grupo de empreendedores experientes, a empresa rapidamente ganhou reconhecimento no mercado por sua abordagem amigável e atendimento personalizado aos clientes. Seu objetivo é ajudar os clientes a alcançarem seus objetivos financeiros de forma segura e responsável. A empresa está em constante crescimento, portanto é bem importante pensar no crescimento da regra de negócio.

Fluxo do Processo de Empréstimo para Empresa X:

    1. Criar Pessoa:
        a. O candidato deve implementar as operações de CRUD para a entidade "Pessoa" utilizando o Spring Boot. A tabela "Pessoa" deve conter os campos: Nome, Identificador, DataNascimento, TipoIdentificador, Valor mínimo mensal das parcelas do empréstimo e Valor máximo de todo o empréstimo.
        b. O campo "TipoIdentificador" deve ser setado baseado no tamanho do identificador recebido, Pessoa Física (PF) como 11 caracteres, Pessoa Jurídica (PJ) como como 14 caracteres, Estudante Universitário (EU)como como 8 caracteres e Aposentado (AP) como como 10 caracteres.
        c. Os valores mínimo e máximo do empréstimo, bem como o valor mínimo das parcelas, são definidos automaticamente com base no "TipoIdentificador".
        d. Pessoa Física (PF):
            i. Valor mínimo mensal das parcelas: R$ 300,00
            ii. Valor máximo de todo o empréstimo: R$ 10.000,00
        e. Pessoa Jurídica (PJ):
            i. Valor mínimo mensal das parcelas: R$ 1000,00
            ii. Valor máximo de todo o empréstimo: R$ 100.000,00
        f. Estudante Universitário (EU):
            i. Valor mínimo mensal das parcelas: R$ 100,00
            ii. Valor máximo de todo o empréstimo: R$ 10.000,00
        g. Aposentado (AP):
            i. Valor mínimo mensal das parcelas: R$ 400,00
            ii. Valor máximo de todo o empréstimo: R$ 25.000,00
