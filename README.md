
% lekerdezes sqlben: app.get("/api/search/:nev",(req,resp)=>{
    const {nev} = req.params
    const sql = "SELECT kategoriak.nev as kategNev, aruk.* from aruk, kategoriak WHERE aruk.kategoriaId = kategoriak.id and aruk.nev  LIKE ?;"
    const values=[`%${nev}%`]
    db.query(sql,values,(err,res)=>{
       if(err){
            console.log(err);
            return resp.status(500).json({error:"Adatbázis hiba!"})
        }
        if(res.length==0){
            return resp.status(404).json({error:"Nincs ilyen virág!"})
        }
        return resp.status(200).send(res)
    })
})
