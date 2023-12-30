import React from 'react'
import { useState } from 'react';
import useApiRequest from '../hooks/useApiRequest';
import "../css/forms.css"
const BookForms = ({ toggleModal, setToggleModal, addBook }) => {
    const init = {
        name : "",
        author : "",
        price : 0
    }
    const divStyle = {
        display : toggleModal ? "block" : "none"
    }
    const [bookData, setBookData] = useState(init);
    const {postData} = useApiRequest(bookData, null);

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
        updatedBookData.price = price;
        setBookData(updatedBookData);
    }
    const createBook = async (e) => {
        e.preventDefault();
        console.log(bookData)
        const resp = await postData({ data : bookData}).then(
          data => {
            //   console.log(data);
            //   setBooks(data);
          }
        )
        setToggleModal(false);
        addBook(bookData)
    }
    const toggleModalToClose = () => {
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
                        onClick={(e) => createBook(e)}>
                            Create
                    </button>
                    <button onClick={() => toggleModalToClose()}>Cancel</button>
                </form>
            </div>
        </div>
    )
}

export default BookForms