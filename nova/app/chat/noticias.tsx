import React, { useState, useEffect } from 'react';
import axios from 'axios';

export default function Noticias() {
    const [news, setNews] = useState([
        {
            id: 0,
            title: '',
            newsContent: ''
        }
    ]);

    useEffect(() => {
        axios.get('http://localhost:8080/companyNews/getAll')
            .then(response => {
                console.log(response.data);
                setNews(response.data);
            })
            .catch(error => {
                console.error(error);
            });
    }, []);

    return (
        <section>
            {news.map((newsItem) => (
                <div key={newsItem.id} className='newsDiv'>
                    <h2 className='newsTitle'>{newsItem.title}</h2>
                    <p className='newsParagraph'>{newsItem.newsContent}</p>
                </div>
            ))}
        </section>
    );
}
