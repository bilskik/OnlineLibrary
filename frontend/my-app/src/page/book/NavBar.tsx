import React from 'react'
import { useNavigate } from 'react-router-dom'
import "./navbar.css"
import { useContext } from 'react'
import { AuthContext } from '../../context/AuthProvider'

const NavBar = () => {
    const nav = useNavigate()
    const { logout } = useContext(AuthContext);

    const handleOnLogout = () => {
        logout()
        nav("/")
    }

    return (
        <nav className='navbar'>
            <h3 onClick={() => nav("/library")} className="library-text">Library</h3>
            <button className='logout' onClick={handleOnLogout} >
                WYLOGUJ
            </button>
        </nav>
    )
}

export default NavBar