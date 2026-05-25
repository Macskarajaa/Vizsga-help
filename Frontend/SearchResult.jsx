import { useState } from 'react';
import { FaHome, FaSearch } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';

export default function Navbar() {
    const [searchWord, setSearchWord] = useState('');
    const navigate = useNavigate();

    return (
        <header
            style={{
                top: 0,
                backgroundColor: 'rgba(190, 98, 0, 0.7)',
                display: 'flex',
                flexDirection: 'row',
                justifyContent: 'space-between',
                padding: '10px',
                height: '60px',
                position: 'fixed',
                width: '100%',
                alignItems: 'center'
            }}
        >
            <button
                type="button"
                style={{
                    border: 'none',
                    background: 'transparent',
                    color: 'white',
                    cursor: 'pointer'
                }}
                onClick={() => navigate('/')}
            >
                <FaHome size={32} />
            </button>

            <div
                style={{
                    display: 'flex',
                    flexDirection: 'row',
                    alignItems: 'center',
                    gap: 6,
                    backgroundColor: 'white',
                    borderRadius: '12px'
                }}
            >
                <input 
                    type="text"
                    value={searchWord}
                    onChange={(e) => setSearchWord(e.target.value)}
                    style={{
                        border: 'none',
                        padding: '10px',
                        background: 'transparent'
                    }}
                    placeholder='Keresés...'
                    onKeyDown={(e) => {
                        if (e.key === 'Enter' && searchWord.trim().length >= 3) {
                            navigate(`/search/${searchWord}`);
                            setSearchWord('');
                        }
                    }}
                />
                <button
                    type='button'
                    style={{
                        border: 'none',
                        background: 'transparent',
                        color: 'gray',
                        cursor: 'pointer',
                        paddingRight: '5px'
                    }}
                    onClick={() => {
                        if (searchWord.trim().length >= 3) {
                            navigate(`/search/${searchWord}`);
                            setSearchWord('');
                        }
                    }}
                >
                    <FaSearch size={25} />
                 </button>
            </div>
        </header>
    )
}
