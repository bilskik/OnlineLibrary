import React from 'react'
import axios from '../axios/axios';

const useApiRequest = ({ data, id}) => {
    const headers =  { 'Content-Type' : 'application/json'}
    const getAllData = async () => {
        // console.log(url)
        try {
            const response = await axios.get("/books", { headers })
            return response.data;

        } catch(err) {
            console.log(err)
        }     
    }
    const getDataById = async () => {
        try {
            const response = await axios.get("/books/id", { headers },  id)
            .then((data) => {
                return data;
            })

        } catch(err) {
            console.log(err)
        }     
    }
    const postData = async ({ data }) => {
        console.log(data);
        try {
            const response = await axios.post("/books", data, { headers })
            .then((data) => {
                return data;
            })

        } catch(err) {
            console.log(err)
        } 
    }
    const updateData = async ({ data }) => {
        console.log(data);
        try {
            const response = await axios.put("/books", data, { headers })
            .then((data) => {
                return data;
            })

        } catch(err) {
            console.log(err)
        } 
    }
    return { getAllData, getDataById, postData, updateData};
}

export default useApiRequest