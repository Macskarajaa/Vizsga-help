.mainContainer {
  background-image: url('/fokep.jpg');
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  width: 100%;
  min-height: 100vh;
  font-family: Arial, Helvetica, sans-serif;
  display: flex;
  align-items: center;
  justify-content: center;
}

.contentContainer {
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  flex-direction: column;
  color: white;
  padding: 15px;
  border-radius: 12px;
  width: fit-content;
  margin: auto;
}

.flowerContainer {
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  flex-direction: column;
  color: white;
  padding: 15px;
  border-radius: 12px;
  max-width: 1100px;
  margin: auto;
}

.scrollArea {
  height: calc(100vh - 250px);
  width: 100%;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
}

.card {
  display: flex;
  flex-direction: row;
  gap: 15px;
}

.cardImgContainer {
  width: 50%;
}

.cardImgContainer > img {
  width: 100%;
  object-fit: cover;
}

.descriptionContainer {
  width: 50%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}

.keszletEsAr {
  display: flex;
  justify-content: space-between;
  flex-direction: row;
}

.cardContainer {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-top: 10px;
}

@media screen and (max-width: 750px) {
  .card {
    flex-wrap: wrap;
  }

  .cardImgContainer {
    width: 100%;
  }

  .descriptionContainer {
    width: 100%;
  }
}