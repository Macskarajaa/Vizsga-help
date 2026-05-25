import { useEffect, useState } from "react";
import { useParams } from "react-router-dom"

export default function SearchResult() {
    const { searchWord } = useParams();
    const [flowers, setFlowers] = useState([]);

    const getFlowers = async () => {
        try {
            const resJSON = await fetch(`http://localhost:3333/api/search/${searchWord}`);
            const result = await resJSON.json();

            if (resJSON.status === 200) setFlowers(result);
            else setFlowers([]);
        } catch (error) {
            console.warn(error);
        }
    }

    useEffect(() => {
        getFlowers();
    }, [searchWord]);

    return (
        <div className="scrollArea">
            <div className="flowerContainer">
                {
                    flowers.length ?
                    <>
                        <p style={{ fontWeight: 'bold', fontSize: '1.2rem' }}>Találatok a keresett kifejezésre: <span style={{ fontSize: '1.3rem' }}>{searchWord}</span></p>
                        <div className="cardContainer">
                            {
                                flowers.map((flower, idx) => (
                                    <div
                                        className="card"
                                        key={flower?.id ?? idx}
                                    >
                                        <div className="cardImgContainer">
                                            <img 
                                                src={flower?.kepUrl} 
                                                alt={flower?.nev}
                                                title={flower?.nev}
                                            />
                                        </div>

                                        <div className="descriptionContainer">
                                            <p style={{ fontWeight: 'bold' }}>{flower?.nev}</p>
                                            <p style={{ textAlign: 'justify', lineHeight: '25px' }}>{flower?.leiras}</p>
                                            <div className="keszletEsAr">
                                                <p>Raktáron: {flower?.keszlet} db</p>
                                                <p>Ár: {flower?.ar} Ft</p>
                                            </div>
                                        </div>
                                    </div>
                                ))
                            }
                        </div>
                    </>
                    :
                    <p style={{ fontWeight: 'bold', fontSize: '1.2rem' }}>A keresett kifejezésre <span style={{ fontSize: '1.3rem' }}>{searchWord}</span> nincs találat</p>
                }
            </div>
        </div>
    )
}