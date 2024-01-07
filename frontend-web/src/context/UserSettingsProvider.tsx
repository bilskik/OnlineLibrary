import { ReactNode, createContext, useState } from "react"
import { useCookies } from "react-cookie";

type UserSettingsContextType = {
    lang : string,
    theme : string,
    changeLang : (lang : string) => void,
    changeTheme : (theme : string) => void,
    callAfterRefresh : () => void
}


export const UserSettingsContext = createContext<UserSettingsContextType>({
    changeLang : () => undefined,
    changeTheme : () => undefined, 
    callAfterRefresh : () => undefined,
    lang : "",
    theme : ""
});


export const UserSettingsProvider = ({ children }: { children : ReactNode }) => {
    const [lang, setLang] = useState<string>("ENG");
    const [theme, setTheme] = useState<string>("light");
    const [cookies, setCookie, _] = useCookies(["lang", "theme"]);
    
    const changeLang = (lang : string) => {
        setCookie("lang",lang)
        setLang(lang);
    }

    const changeTheme = (theme_ : string) => {
        console.log(theme_)
        setCookie("theme", theme_);
        setTheme(theme_)
    }

    const callAfterRefresh = () => {
        if(cookies.lang) {
            setLang(cookies.lang)
        } 
        if(cookies.theme) {
            setTheme(cookies.theme)
        }
    }

    return (
        <UserSettingsContext.Provider value={{ changeLang, changeTheme, callAfterRefresh, lang, theme }}>
            { children }
        </UserSettingsContext.Provider>
    )
}