import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Home() {

    const [categories, setCategories] = useState([]);
    const navigate = useNavigate();

    const getCategories = async () => {
        try {
            const resJSON = await fetch(`http://localhost:3333/api/categories`);
            const result = await resJSON.json();
            setCategories(result);
        } catch (error) {
            console.warn(warn)
        }
    }

    useEffect(() => {
        getCategories();
    }, []);

    return (
        <div className="scrollArea">
            <div
                className="contentContainer"
                style={{
                    gap: 30,
                    textAlign: 'center'
                }}
            >
                <p style={{ fontWeight: 'bold' }}>Vetőmagok - Mindenféle, minden mennyiségben</p>
                <p>Válasszon az alábbi termék kategóriákból:</p>
                
                {
                    categories.length &&
                    categories.map((category, idx) => (
                        <button
                            type="button"
                            style={{
                                background: 'transparent',
                                border: 'none',
                                color: 'white',
                                textDecoration: 'underline',
                                letterSpacing: '6px',
                                fontSize: '1.3rem',
                                fontWeight: 'bold',
                                cursor: 'pointer'
                            }}
                            key={category?.id ?? idx}
                            onClick={() => navigate(`/flowers/${category?.id}`)}
                        >
                            {category?.nev}
                        </button>
                    ))

                }
            </div>
        </div>
    )
}