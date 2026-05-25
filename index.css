import './App.css';
import Navbar from './Components/Navbar';
import Footer from './Components/Footer';
import { Routes, Route } from 'react-router-dom';
import Home from './Components/Home';
import FlowersByCateg from './Components/FlowersByCateg';
import SearchResult from './Components/SearchResult';

function App() {

  return (
    <div className='mainContainer'>
      <Navbar />

      <div style={{ minHeight: '60px', top: 0 }}/>

      <div
        style={{
          right: 0,
          top: 60,
          position: 'fixed',
          color: 'white',
          fontSize: '2rem',
          fontWeight: 'bold'
        }}
      >
        <p>Nevenincs Bt.</p>
      </div>
      
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/flowers/:id' element={<FlowersByCateg />} />
        <Route path='/search/:searchWord' element={<SearchResult />} />
      </Routes>
      
      <div style={{ minHeight: '60px', bottom: 0 }}/>

      <Footer />
    </div>
  )
}

export default App
