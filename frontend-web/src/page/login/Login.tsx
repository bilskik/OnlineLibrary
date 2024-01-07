import React, { useState, useContext } from 'react'
import axios from '../../axios/axios';
import { AuthContext } from '../../context/AuthProvider';
import { useNavigate } from 'react-router-dom';
import "./login.css"
import { UserSettingsContext } from '../../context/UserSettingsProvider';
import { resolveData } from '../../service/resolveData';

type UserType = {
    username : string,
    password : string
}
const initUser = {
    username : "",
    password : ""
}
const initErr = { 
    isUserErr : false,
    isPassErr : false,
    errPassMsg : "",
    errUserMsg : ""
}

const Login = () => {
    const [user, setUser] = useState<UserType>(initUser);
    const [err, setErr] = useState(initErr)
    const [page, setPage] = useState({ isLogin : true });
    const { login } = useContext(AuthContext);
    const nav = useNavigate();
    const { lang, theme } = useContext(UserSettingsContext);
    const data = resolveData(lang);

    const handleOnLogin = (e : React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        e.preventDefault()
        const isErr = isValid();
        if(!isErr) {
            axios.post("/login",user)
                .then((res) => {
                    login(res.data.jwt)
                    nav("/library")
                })
                .catch(() => {

                })
        }

    }

    const handleOnRegister = (e : React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        e.preventDefault();
        const isErr = isValid();
        if(!isErr) {
            axios.post("/register",user)
                .then((res) => {
                    login(res.data.jwt)
                    nav("/library")
                })
                .catch(() => {

                })
        }
    }

    const isValid = () => {
        const { username, password } = user;
        let isErr = false;
        const trimmedUsername = username.trim();
        if(!trimmedUsername) {
            setErr(prev => ({
                ...prev,
                isUserErr : true,
                errUserMsg : data.blankUsernameError ? data.blankUsernameError : ""
            }))
            isErr = true;
        } else {
            setErr(prev => ({
                ...prev,
                isUserErr : false,
                errUserMsg : ""
            }))
        }
        const trimmedPassword = password.trim();
        if(!trimmedPassword) {
            setErr(prev => ({
                ...prev,
                isPassErr : true,
                errPassMsg : data.blankPasswordError ? data.blankPasswordError : ""
            }))
            isErr = true;
        } else {
            setErr(prev => ({
                ...prev,
                isPassErr : false,
                errPassMsg : ""
            }))
        }
        return isErr;
    }

    return (
        <div className='login-container'>
        <form className='form-container'>
            <h2 className="login-header">
               {
                    page.isLogin ? ( data.loginTitle ) : ( data.registerTitle ) 
               }
            </h2>
            <div className='form-group-user'>
                <label id="user">
                    { data.user }
                </label>
                <input
                    id='user'
                    name='user'
                    type='text'
                    value={user.username}
                    onChange={(e) => setUser({...user, username : e.target.value})}
                />
                {
                    err.isUserErr ? <p className='err-msg'>{ err.errUserMsg }</p> : null
                }
            </div>
            <div className='form-group-password'>
                <label htmlFor='password'>
                    { data.password }
                </label>
                <input
                    id='password'
                    name='password'
                    type='password'
                    value={user.password}
                    onChange={(e) => setUser({ ...user, password: e.target.value })}
                />
                {
                    err.isPassErr ? <p className='err-msg'>{ err.errPassMsg }</p> : null
                }
                {
                    page.isLogin ?               
                    <p className='register-acc'>{ data.needAnAcc }
                        <span onClick={(e) => { setPage({ isLogin : false}); setUser(initUser); setErr(initErr)}}>{ data.goToRegister }</span>
                    </p> :
                    <p className='register-acc'>{ data.alreadyHaveAnAcc }
                        <span onClick={(e) => { setPage({ isLogin : true}); setUser(initUser); setErr(initErr) }}>{ data.goToLogin }</span>
                    </p> 
                }

            </div>
            {
                page.isLogin ? 
                <button onClick={(e) => handleOnLogin(e)}>
                    { data.loginBtn }
                </button>
                :
                <button onClick={(e) => handleOnRegister(e)}>
                    { data.registerBtn }
                </button>
            }
        </form>
    </div>
    )
}

export default Login