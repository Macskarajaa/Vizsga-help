DUMP: (DATUMKERESO)  const handleSearch = () => {
    if (!selectedDate) {
      alert("Kérlek válassz ki egy dátumot!");
      return;
    }
    // Build the URL exactly like yesterday: base + route + param
    const formattedDate = selectedDate.replace(/-/g, '. ') + '.';
    setSearchedDate(formattedDate);

    const targetUrl = `http://localhost:8000/api/namesbydate/${selectedDate}`;
    
    // Call the utility function
    getNamesFiltered(targetUrl, setNames);
  };

  return (
    <div className='FindNamesContainer'>
      <div className="Icon" onClick={() => navigate("/")}>
        <FaHome />
      </div>
      <div className="FindNamesButtons">
        <input 
          type="date" 
          value={selectedDate} 
          onChange={(e) => setSelectedDate(e.target.value)} 
        />
        <button onClick={handleSearch}>Keresés</button>
      

      {/* Render the names list below */}
      <div className="Results" style={{ color: 'white', marginTop: '15px' }}>
        {searchedDate && (
          <h3>Névnap/névnapok ({searchedDate})</h3>
        )}
        {names.map((item, index) => (
          <p key={index}>{item.name}</p> 
        ))}
      </div>
    </div>
    </div>
