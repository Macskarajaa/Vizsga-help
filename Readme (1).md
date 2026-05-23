# Java help

## CLI

### Osztály létrehozása :

```
public class Kigyo {
    private String fajta;
    private int hossz;
    private String elofordulas;
    private Boolean merges;

    public Kigyo(String sor){
        String tomb[] = sor.split(";");
        fajta = tomb[0];
        hossz = Integer.parseInt(tomb[1]);
        elofordulas = tomb[2];
        merges = tomb[3].equals("Igen")?true:false;
     }

    public String getFajta() {
        return fajta;
    }

    public int getHossz() {
        return hossz;
    }

    public String getElofordulas() {
        return elofordulas;
    }

    public Boolean getMerges() {
        return merges;
    }

    @Override
    public String toString() {
        return String.format("%s (%dcm)",fajta,hossz);
    }
}
```

### Fájlbeolvasás:

```
        List<Leltar> leltar = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("leltar.csv"), "UTF-8")) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                leltar.add(new Leltar(scanner.nextLine()));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
```

### Map.Entry

```

    int legtobbOsszertek = 0;
    int legtobbesEv = 0;
    for (Map.Entry<Integer, Integer> e : beszerzesek.entrySet()) {
        if (legtobbOsszertek < e.getValue()) {
            legtobbOsszertek = e.getValue();
            legtobbesEv = e.getKey();
        }
    }
```

### Class sorting
````
    Collections.sort(madarak,(a,b) -> a.getSuly() - b.getSuly())
````

### TreeMap kategorizálás
````
    TreeMap<Integer,Integer> map = new Treemap<>();
    for(Fovaros elem:varosok){
        int alsoHatar = (elem.getFovarosLakossag() / 5000000) * 5000000;

        if(!map.containskey(alsoHatar)){
            map.put(alsohatar,1);
        }else{
            map.merge(alsohatar,1,(a,b)->a+b);
        }
    }

    for(var e:map.entrySet()){
        int also = e.getKey();
        int felso = also + 4999999;
        System.out.printf("%12s - %12s: %d\n",String.format("%d",also),String.format("%d",felso),e.getValue()) 
    }
````

### Files.write:

````
        List<Leltar> elsoEv = leltar.stream().filter(obj->obj.getBeszerzesiEv()==2018).toList();
        List<String> elsoEvStr = elsoEv.stream().map(obj->obj.toString()).toList();
        
        System.out.println("\n6) Az első év adatai kiírva a kezdes.txt fájlba");
        try{
            Files.write(Paths.get("kezdes.txt"),elsoEvStr,StandardCharsets.UTF_8);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
````

## GUI
### ComboBoxos
````
    List<Integer> evList = new ArrayList<>();

    ObservableList<Diafilm> diafilmek = FXCollections.observableArrayList();

    private FileChooser fc = new FileChooser();
    
````

### Initialize

````
    public void initialize(){
        evCb.setDisable(true);
        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájlok","*.csv"));
    }
````

### Megnyitas sima

````
   public void onMegnyitasClick(ActionEvent actionEvent) {
        File fbe = fc.showOpenDialog(varosokView.getScene().getWindow());
        varosokView.getItems().clear();
        readData(fbe);
        System.out.println("beolvasott adatok: " + utazok.size());
        varosok = new ArrayList<>(utazok.stream().map(obj->obj.getVaros()).distinct().sorted().toList());
        varosok.forEach((obj)->varosokView.getItems().add(obj));
        varosokView.getSelectionModel().select(0);
        szures();
    }
````

### Megnyitas cb

````

    public void onMegnyitasClick(ActionEvent actionEvent) {
        File fbe = fc.showOpenDialog(diakView.getScene().getWindow());
        readData(fbe);
        System.out.println("beolvasott adat: " + diafilmek.size());
        if(diafilmek.size()!=0){
            feketeFeherCb.setDisable(false);
            szinesCb.setDisable(false);
            evList = diafilmek.stream().map(obj->obj.getEv()).distinct().sorted().toList();
            for(Integer elem:evList){
                evCb.getItems().add(elem);
            }
            evCb.getSelectionModel().select(0);
            evCb.setDisable(false);
            feketeFeherCb.setSelected(true);
            szinesCb.setSelected(true);
            szures();
        }
    }
````


### Readdata

````
    public void readData(File fbe){
        utazok.clear();
        try(Scanner scanner = new Scanner(fbe)){
            while (scanner.hasNextLine()){
                utazok.add(new Utazo(scanner.nextLine()));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
````

### Szures

````
    public void szures(ActionEvent actionEvent) {
        keresView.getItems().clear();
        String keresSzo = keresField.getText().toLowerCase();
        List<Kigyo> keres = kigyok.stream().filter(obj->obj.getFajta().toLowerCase().contains(keresSzo)).toList();
        keres.forEach((obj)->keresView.getItems().add(String.format("%s",obj.getFajta())));
    }
````

### Szures cb

````
    public void szures(){
        int evIndex = evCb.getSelectionModel().getSelectedIndex();
        adatView.getItems().clear();
        List<Integer> sortedEvek = evList.stream().sorted().toList();
        int ev = sortedEvek.get(evIndex);
        List<Leltar> keres = leltar.stream().filter(obj->obj.getBeszerzesiEv()==ev).toList();
        keres.forEach((obj)->adatView.getItems().add(String.format("%d x %s = %,d,-Ft",obj.getDarab(),obj.getMegnevezes(),obj.getDarab()*obj.getEgysegar())));
        int darabCount = 0;
        int osszar = 0;
        for(Leltar elem:keres){
            darabCount+=elem.getDarab();
            osszar+=elem.getDarab()*elem.getEgysegar();
        }
        adatLabel.setText(String.format("%d darab / %,d,-Ft",darabCount,osszar));

    }
````

### Menu

````
    public void onKilepesClick(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void onNevjegyClick(ActionEvent actionEvent) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Névjegy");
        info.setHeaderText(null);
        info.setContentText("Leltár v1.0.0\n (C) Kandó");
        info.showAndWait();
    }
````

## Backend

### Importok

```
pnpm i express, cors, mysql2, nodemon
```

### configDB
````
export const configDB={
    host:"localhost",
    user:"root",
    password:"",
    database:"filmek"
}
````

### Fajl importok

````
import express from "express"
import mysql from "mysql2"
import cors from "cors"
import { configDB } from "./configDB.js"
````

### Letrehozas

````
const db=mysql.createConnection(configDB)
const app=express()
app.use(cors())
app.use(express.json())
const port = 88

app.listen(port,()=>console.log(`server listening on port:${port}`))
````

### Get sima

````
app.get("/",(req,res)=>res.send("<h1>Békák v1.0.0</h1>"))
````

### GET /nev

````
app.get("/bekak",(req,resp)=>{
    const sql = "SELECT id, nev, latin_nev,elohely from fajok order by 1"
    db.query(sql,(err,res)=>{
        if(err){
            console.log(err);
            resp.status(500).json({error:"Adatbázis hiba!"})
        }else{
            resp.status(200).send(res)
        }
    })
})

app.get("/figyelok",(req,resp)=>{
    const sql = "SELECT DISTINCT(megfigyelo) from megfigyelesek order by 1"
    db.query(sql,(err,res)=>{
        if(err){
            console.log(err);
            resp.status(500).json({error:"Adatbázis hiba!"})
        }else{
            resp.status(200).send(res)
        }
    })
})
````
### GET /valami:id

````
app.get("/kilatta/:bekaid",(req,resp)=>{
    const {bekaid} = req.params
    const sql = "SELECT megfigyelo, helyszin, datum from megfigyelesek WHERE bekaid = ? order by datum;"
    const values=[bekaid]
    db.query(sql,values,(err,res)=>{
       if(err){
            console.log(err);
            return resp.status(500).json({error:"Adatbázis hiba!"})
        }
        if(res.length==0){
            return resp.status(404).json({error:"Ilyen békát nem látott senki!"})
        }
        return resp.status(200).send(res)
    })
})
````

### POST

````
app.post("/megfigyeles",(req,resp)=>{
    const {bekaid,helyszin, datum, megfigyelo, egyedszam} = req.body
    const sql = "INSERT into megfigyelesek set bekaid = ?, helyszin = ?, datum = ?, megfigyelo = ?, egyedszam = ?"
    if(bekaid&&helyszin&&datum&&megfigyelo&&egyedszam){
        const values=[ bekaid,helyszin, datum, megfigyelo, egyedszam]
        db.query(sql,values,(err,res)=>{
        if(err){
            console.log(err);
            return resp.status(500).json({error:"Adatbázis hiba!"})
        }
        return resp.status(200).send(res)
        })
    }else{
        return resp.status(400).json({error:"Hibás paraméterek!"})
    }
})

async function postMonitor(req,res) {
    const { tipus, meret} = req.body
    if(tipus&&meret){
        const sql = "insert into monitor set tipus =?, meret=?"
        const [ adat ] = await con.execute(sql,[tipus,meret])

        res.status(201).send({msg:"Monitor hozzáadva!"})
    } else {
        res.status(400).send({ error:"Hiányos paraméterek!" })
    }
}
````

### PUT

````
app.put("/egyedszam/:id/:szam",(req,resp)=>{
    const {id,szam} = req.params
    const sql = "UPDATE megfigyelesek set egyedszam = ? WHERE id = ?"
    const values = [szam,id]
    db.query(sql,values,(err,res)=>{
        if(err){
            console.log(err);
            return resp.status(500).json({error:"Adatbázis hiba!"})
        }
        if(res.affectedRows==0){
            return resp.status(400).json({error:"Nem létező ID!"})
        }else{
            return resp.status(200).send(res)
        }
    })
})
````

### DELETE

````
app.delete("/megfigyeles/:id",(req,resp)=>{
    const {id} = req.params
    const sql = "DELETE from megfigyelesek where id = ?"
    const values = [id]
    db.query(sql,values,(err,res)=>{
        if(err){
            console.log(err);
            return resp.status(500).json({error:"Adatbázis hiba!"})
        }
        if(res.affectedRows==0){
            return resp.status(404).json({error:"Nem létező ID!"})
        }
        return resp.status(200).send(res)
    })
})
````

## Frontend

### Importok

````
pnpm i axios, react-router-dom
````

## utils

````
import axios from "axios";

const baseUrl = "http://localhost:88"
````

## GET

````
export const getOsztalyok = async (setOsztalyok,setSelectedOsztaly) => {
    const resp = await axios.get(baseUrl+"/osztalyok")
    setOsztalyok(resp.data)
    setSelectedOsztaly(resp.data[0].oaz)
}
````

````
export const getTanulok = async (id,setTanulok) => {
    const resp = await axios.get(baseUrl+"/tanulok/"+id)
    setTanulok(resp.data)
}
````

### ADD

````
export const addTanulo = async (newTanulo) => {
    const resp = await axios.post(baseUrl+"/tanulok",newTanulo)
    return resp.data
}
````

### DELETE

````
export const deleteTanulo = async (id) => {
    const resp = await axios.delete(baseUrl+"/tanulo/"+id)
    return resp.data
} 
````

### UPDATE

````
export const updateTanulo = async (id, updateData) => {
    const resp = await axios.put(baseUrl+"/tanulo/"+id)
    return resp.data
}
````

### Routing

````
    <BrowserRouter>
    <Routes>
    <Route path='/' element={ <Home/> } />
        <Route path='/foodsbycategory/:categId' element={ <Foods /> } />
        <Route path='/foodsbysearch/:searchedWord' element={ <Foods /> } />
    </Routes>
    </BrowserRouter>
````

### Urlap

````
    const [nev , setNev] = useState("")
    const [kor, setKor] = useState("")
    const [nem, setNem] = useState("L")
    const [kep, setKep] = useState("")
    const [ujTanulo, setUjTanulo] = useState({nev:"",kor:"",nem:"",kep:"",oaz:140})
    useEffect(()=>{
        if(tanulo){
            setNev(tanulo.nev)
            setKor(tanulo.kor)
            setNem(tanulo.nem)
            setKep(tanulo.kep)
        }
    },[tanulo])

    const handleAdd = () => {
        let kolbasz = {nev:nev,kor:kor,nem:nem,kep:kep,oaz:140}
        setUjTanulo(kolbasz)
        console.log(ujTanulo);
        
        addTanulo(kolbasz)
    }

    <div className='urlap'>
        <div className='sor'>
            <label htmlFor="">Név:</label>
            <input type="text" value={nev} onChange={(e)=>setNev(e.target.value)}/>
            <label htmlFor="">Kor:</label>
            <input type="text" value={kor} style={{width:"50px"}} onChange={(e)=>setKor(e.target.value)}/>
            <label htmlFor="">Nem:</label>
            <label htmlFor="">Lány</label>
            <input type="radio" name="nem" id="" checked={nem=="L"} onChange={()=>setNem("L")} />
            /
            <input type="radio" name="nem" id="" checked={nem=="F"} onChange={()=>setNem("F")}/>
            <label htmlFor="">Fiú</label>
        </div>
        <div className='sor'>
            <label htmlFor="">Kép:</label>
            <input type="text" style={{width:"100%"}} value={kep} onChange={(e)=>setKep(e.target.value)} />
        </div>
        <div className='sor'>
            <button onClick={handleAdd}>Felvesz</button>
            <button>Módosít</button>
            <button>Töröl</button>
            <button>Mégsem</button>
        </div>
    </div>
````