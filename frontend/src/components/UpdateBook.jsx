import React from 'react'
import { useState } from 'react';
import useApiRequest from '../hooks/useApiRequest';
const UpdateBook = ({ toggleModal, setToggleModal, bookDataInit, updateNewBook}) => {
    const [bookData, setBookData ] = useState(bookDataInit[0]);
    const { updateData } = useApiRequest(bookData, null)
    const divStyle = {
        display : toggleModal ? "block" : "none"
    }
    const handleNameOnChange = (name) => {
        const updatedBookData = { ...bookData };
        updatedBookData.name = name;
        setBookData(updatedBookData);
    }
    const handleAuthorOnChange = (author) => {
        const updatedBookData = { ...bookData };
        updatedBookData.author = author;
        setBookData(updatedBookData);
    }
    const handlePriceOnChange = (price) => {
        const updatedBookData = { ...bookData };
        updatedBookData.price = parseFloat(price);
        setBookData(updatedBookData);
    }
    const toggleModalToClose = (e) => {
        e.preventDefault();
        setToggleModal(false);
    }
    const updateBook = async (e) => {
        e.preventDefault();
        console.log(bookData);
        const headers =  { 'Content-Type' : 'application/json'}        
        const resp = await updateData({ data : bookData})
        updateNewBook(bookData);
        setToggleModal(false);
    }
    return (
        <div className='modal' id="modal" style={divStyle}>
            <div className='modal-content'>
                <form>
                    <label>Name</label>
                    <input 
                        type='text'
                        value={bookData.name}

                        onChange={(e) => handleNameOnChange(e.target.value)}
                    />
                    <label>Author</label>
                    <input 
                        type='text'
                        value={bookData.author}
                        onChange={(e) => handleAuthorOnChange(e.target.value)}
                    />
                    <label>Price</label>
                    <input 
                        type='text'
                        value={bookData.price}
                        onChange={(e) => handlePriceOnChange(e.target.value)}
                    />
                    <button 
                        type="submit" 
                        onClick={(e) => updateBook(e)}>
                            Update
                    </button>
                    <button onClick={(e) => toggleModalToClose(e)}>Cancel</button>
                </form>
            </div>
        </div>
    )
}

export default UpdateBook