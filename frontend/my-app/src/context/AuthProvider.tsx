import React, { ReactNode, useState, createContext } from 'react'

type AuthProviderPropsType = {
    children : ReactNode
}

type ContextType = {
    getJwt : () => string,
    login : (jwt : string) => void,
    logout : () => void
}

export const AuthContext = createContext<ContextType>({
    getJwt : () => "",
    login : () => undefined,
    logout : () => undefined
});

export const AuthProvider = ({ children } : AuthProviderPropsType) => {
    const [jwt, setJwt] = useState<string>("");
    
    const login = (jwt : string) => {
        if(jwt !== "") {
            localStorage.setItem('jwt', jwt);
            setJwt(jwt)
        }
    }

    const logout = () => {
        localStorage.removeItem('jwt');
        setJwt(jwt)
    }
    
    const getJwt = () => {
        if(!jwt) {
            const retJwt = localStorage.getItem('jwt');
            if(retJwt) {
                setJwt(retJwt);
                return retJwt;
            }
        }
        return jwt;
    }
    
    return (
        <AuthContext.Provider value= {{ getJwt, login, logout }}>
            { children }
        </AuthContext.Provider>
    )
}