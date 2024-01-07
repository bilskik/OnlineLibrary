export const resolveData = (lang : string) => {
    if(lang === "ENG") {
        return {
            loginTitle : "Login",
            user : "User",
            password : "Password",
            needAnAcc : "Need an account?",
            goToRegister : "REGISTER HERE",
            loginBtn : "LOGIN",

            blankUsernameError : "Username is blank!",
            blankPasswordError : "Password is blank!",

            registerTitle : "Register",
            alreadyHaveAnAcc : "Already have an account?",
            goToLogin : "LOGIN HERE",
            registerBtn : "REGISTER",
            
            title : "Library",
            logoutBtn : "LOGOUT",
            author : "Author",
            name : "Name",
            edit : "Edit",
            delete : "Delete",
            createNew : "Create new",

            titleModalPostBook : "Add new book",
            titleModalEditBook : "Edit book",
            submitModal : "Submit",
            cancelModal : "Cancel",
            modalNamePlaceholder  : "Enter book name",
            modalAuthorPlaceholder : "Enter author name"
        }
    } else if(lang === "POL") {
        return {
            loginTitle : "Logowanie",
            user : "Użytkownik",
            password : "Hasło",
            needAnAcc : "Potrzebujesz nowego konta?",
            goToRegister : "ZAREJSTRUJ SIĘ",
            loginBtn : "ZALOGUJ",

            blankUsernameError : "Użytkownik jest pusty!",
            blankPasswordError : "Hasło jest puste!",

            registerTitle : "Rejestracja",
            alreadyHaveAnAcc : "Masz już konto?",
            goToLogin : "ZALOGUJ SIĘ",
            registerBtn : "ZAREJSTRUJ SIĘ",

            title : "Księgarnia",
            logoutBtn : "WYLOGUJ",
            author : "Autor",
            name : "Nazwa",
            edit : "Edytuj",
            delete : "Usuń",
            createNew : "Dodaj nową",

            titleModalPostBook : "Dodaj nową książkę",
            titleModalEditBook : "Edytuj książkę",
            submitModal : "Zatwiedź",
            cancelModal : "Anuluj",
            modalNamePlaceholder  : "Wprowadź nazwę książki",
            modalAuthorPlaceholder : "Wprowadź imię autora"
        }
    } else {
        return {

        }
    }
}