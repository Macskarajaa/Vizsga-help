import { FaPhone } from "react-icons/fa";
import { CiMail } from 'react-icons/ci';

export default function Footer() {

    return (
        <footer
            style={{
                bottom: 0,
                backgroundColor: 'rgba(190, 98, 0, 0.7)',
                display: 'flex',
                flexDirection: 'row',
                justifyContent: 'center',
                alignItems: 'center',
                height: '60px',
                position: 'fixed',
                width: '100%',
                flexWrap: 'wrap',
                color: 'white',
                gap: 6,
                fontSize: '1.1rem'
            }}
        >
            <p><span style={{ fontWeight: 'bold' }}>Kapcsolat: </span><FaPhone /> 06-70/111-1111</p>
            <p><CiMail /> nevenincsbt@gmail.com</p>
        </footer>
    )
}