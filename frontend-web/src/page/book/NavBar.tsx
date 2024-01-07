import { useNavigate } from 'react-router-dom'
import "./navbar.css"
import { useContext } from 'react'
import { AuthContext } from '../../context/AuthProvider'
import { UserSettingsContext } from '../../context/UserSettingsProvider'
import { resolveData } from '../../service/resolveData'
import { FaMoon } from "react-icons/fa";
import { MdOutlineWbSunny } from "react-icons/md";

const NavBar = () => {
    const nav = useNavigate()
    const { logout } = useContext(AuthContext);
    const { lang, theme, changeLang, changeTheme } = useContext(UserSettingsContext);
    const data = resolveData(lang);

    const handleOnLangChange = (lang_ : string) => {
        changeLang(lang_)
    }

    const handleOnThemeChange = (theme_ : string) => {
        changeTheme(theme_)
    }

    const handleOnLogout = () => {
        logout()
        nav("/")
    }

    return (
        <nav className='navbar'>
            <h3 onClick={() => nav("/library")} className="library-text">{ data.title }</h3>
            <div className='navbar-right'>
                <div className='theme-choice'>
                    <FaMoon 
                        style={theme === "dark" ? {fontSize : "1.1rem", fontWeight : "bold"}  : {}}
                        onClick={() => handleOnThemeChange("dark")}
                    />
                    /<MdOutlineWbSunny 
                        style={theme === "light" ? {fontSize : "1.3rem", fontWeight : "bold"} : {}} 
                        onClick={() => handleOnThemeChange("light")}
                    />
                </div>
                <span className='language-choice'>
                    <span 
                        className={lang === "ENG" ? "curr-choice" : ""}
                        onClick={() => handleOnLangChange("ENG")}>
                        EN
                    </span>/
                    <span 
                        className={lang === "POL" ? "curr-choice" : ""}
                        onClick={() => handleOnLangChange("POL")}>
                        POL
                    </span>
                </span>
                <button className='logout' onClick={handleOnLogout} >
                    { data.logoutBtn }
                </button>
            </div>
        </nav>
    )
}

export default NavBar