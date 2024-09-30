# Sustentare - Reviva. Reuse. Recicle.

## Sumário
- [Visão Geral](#visão-geral)
- [Principais Funcionalidades](#principais-funcionalidades)
- [Regras de Negócio](#regras-de-negócio)
- [Arquitetura MVVM](#arquitetura-mvvm)
- [Screenshots](#screenshots)

## Visão Geral

O aplicativo Sustentare foi desenvolvido para promover ações sustentáveis em ambientes corporativos, incentivando os colaboradores a adotarem práticas mais ecológicas no dia a dia. Com o Sustentare, os usuários podem visualizar conteúdos educativos, participar de desafios de sustentabilidade, calcular emissões de CO2 e gerenciar seu perfil.

O projeto utiliza a arquitetura MVVM (Model-View-ViewModel), que facilita a separação de responsabilidades e contribui para um código mais organizado e fácil de manter.

## Principais Funcionalidades

### 1. **Educação Sustentável**
   - A tela de **Educação Sustentável** fornece informações detalhadas sobre práticas ecológicas, oferecendo dicas e tutoriais sobre como reduzir o impacto ambiental. 
   - Os usuários podem clicar em **Leia mais** para visualizar o conteúdo detalhado.

### 2. **Lista de Tópicos e Desafios**
   - A **Lista de Tópicos** apresenta uma série de desafios que os colaboradores podem realizar, como "Uso de garrafas reutilizáveis" e "Economia de energia".
   - Cada tópico possui uma tela de **Desafio**, onde o usuário pode descrever sua contribuição e anexar evidências (fotos ou vídeos).
   - O botão **Salvar** só é habilitado se tanto a descrição quanto o arquivo estiverem preenchidos, garantindo que todas as informações necessárias sejam fornecidas.

### 3. **Calculadora de Emissão de CO2**
   - A tela de **Cálculo de Emissão de CO2** permite ao usuário calcular sua contribuição para a pegada de carbono no ambiente de trabalho, levando em consideração o consumo de energia, papel e plástico.

### 4. **Perfil do Usuário**
   - A tela de **Perfil** permite que o usuário visualize suas informações, como nome e e-mail, além de realizar logout ou retornar à página anterior.

## Regras de Negócio
- **Desafios:** Um desafio só pode ser salvo se o usuário fornecer uma descrição e anexar uma mídia (foto ou vídeo). Caso contrário, o botão "Salvar" permanece desabilitado.
- **Conclusão de Desafios:** Quando um desafio é salvo, ele é marcado como concluído. Na **Lista de Tópicos**, um ícone de check indica os desafios já finalizados.
- **Deleção de Desafios:** O usuário pode deletar sua resposta para um desafio, o que também remove a mídia associada. Após a deleção, o aplicativo retorna para a **Lista de Tópicos**.

## Arquitetura MVVM

O projeto faz uso da arquitetura **MVVM (Model-View-ViewModel)** para facilitar a manutenção e a escalabilidade do aplicativo. A seguir, detalhamos os componentes do MVVM utilizados:

### **Model**
O **Model** é responsável por representar os dados do aplicativo. Em nosso projeto, usamos o Firebase para persistência de dados, sendo as informações do usuário e dos desafios armazenadas e recuperadas de lá.

### **View**
As **Views** são as interfaces com o usuário, desenvolvidas com **Jetpack Compose**. Cada tela é um composable que observa mudanças no **ViewModel** para reagir a atualizações de estado.

### **ViewModel**
Os **ViewModels** são responsáveis por manter o estado da UI e gerenciar a lógica de negócios. Utilizamos **LiveData** e **State** para manter os estados da interface e garantir a reatividade. 
Exemplo de uso de **ViewModel**:
- **`AuthViewModel`**: Gerencia a autenticação do usuário, verificando se ele está autenticado e providenciando o logout.
- **`EducationalViewModel`**: Gerencia o conteúdo educativo, como carregar artigos e detalhes dos tópicos.

## Conclusão

O Sustentare oferece uma forma interativa de engajar os colaboradores em práticas sustentáveis no ambiente de trabalho, com um design claro e uma navegação intuitiva. Através do uso da arquitetura MVVM, o projeto garante uma estrutura limpa e separação de responsabilidades, o que facilita o desenvolvimento e a manutenção contínua.
