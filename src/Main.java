import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Passagier> passagiers = new ArrayList<>();
        ArrayList<Vlucht> vluchten = new ArrayList<>();
        ArrayList<Ticket> tickets = new ArrayList<>();
        ArrayList<Personeel> personeelLeden = new ArrayList<>();

        while (true) {
            System.out.println("\n--- Luchthaven Menu ---");
            System.out.println("1. Nieuwe passagier aanmaken");
            System.out.println("2. Nieuwe vlucht aanmaken");
            System.out.println("3. Nieuw ticket aanmaken");
            System.out.println("4. Passagier boarden");
            System.out.println("5. Personeel toewijzen aan vlucht");
            System.out.println("6. Print vluchtinfo");
            System.out.println("7. Afsluiten");
            System.out.print("Kies een optie: ");
            int keuze = scanner.nextInt();
            scanner.nextLine();

            switch (keuze) {
                case 1:
                    System.out.print("Naam: ");
                    String naam = scanner.nextLine();
                    System.out.print("Leeftijd: ");
                    int leeftijd = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Adres: ");
                    String adres = scanner.nextLine();
                    passagiers.add(new Passagier(naam, leeftijd, adres));
                    System.out.println("Passagier toegevoegd!");
                    break;

                case 2:
                    System.out.print("Vluchtcode: ");
                    String vluchtCode = scanner.nextLine();
                    System.out.print("Eindbestemming: ");
                    String bestemming = scanner.nextLine();
                    System.out.print("Aantal plaatsen economy: ");
                    int economyPlaatsen = scanner.nextInt();
                    System.out.print("Aantal plaatsen business: ");
                    int businessPlaatsen = scanner.nextInt();
                    vluchten.add(new Vlucht(vluchtCode, bestemming, economyPlaatsen, businessPlaatsen));
                    System.out.println("Vlucht toegevoegd!");
                    break;

                case 3:
                    System.out.print("Passagier ID (0 - " + (passagiers.size()) + "): ");
                    int passagierId = scanner.nextInt();
                    if (passagierId < 0 || passagierId >= passagiers.size()) {
                        System.out.println("Ongeldig passagier-ID! Probeer opnieuw.");
                        break;
                    }

                    System.out.print("Vlucht ID (0 - " + (vluchten.size() - 1) + "): ");
                    int vluchtId = scanner.nextInt();
                    if (vluchtId < 0 || vluchtId >= vluchten.size()) {
                        System.out.println("Ongeldig vlucht-ID! Probeer opnieuw.");
                        break;
                    }

                    System.out.print("Klasse (1 = Economy, 2 = Business): ");
                    int klasseKeuze = scanner.nextInt();
                    if (klasseKeuze != 1 && klasseKeuze != 2) {
                        System.out.println("Ongeldige keuze voor klasse! Probeer opnieuw.");
                        break;
                    }

                    Vlucht geselecteerdeVlucht = vluchten.get(vluchtId);
                    Passagier geselecteerdePassagier = passagiers.get(passagierId);
                    String gekozenKlasse = (klasseKeuze == 1) ? "Economy" : "Business";
                    Ticket ticket = new Ticket(geselecteerdePassagier, geselecteerdeVlucht, gekozenKlasse);
                    tickets.add(ticket);

                    System.out.println("Ticket toegevoegd voor " + geselecteerdePassagier.getNaam() + " in " + gekozenKlasse + "-klasse!");
                    break;

                case 4:
                    System.out.print("Passagier ID: ");
                    int boardPassagierId = scanner.nextInt();
                    System.out.print("Vlucht ID: ");
                    int boardVluchtId = scanner.nextInt();
                    Vlucht boardVlucht = vluchten.get(boardVluchtId);
                    Passagier boardPassagier = passagiers.get(boardPassagierId);
                    boardVlucht.voegPassagierToe(boardPassagier);
                    System.out.println("Passagier geboard!");
                    break;

                case 5:
                    System.out.print("Personeelslid naam: ");
                    String personeelNaam = scanner.nextLine();
                    System.out.print("Vlucht ID: ");
                    int personeelVluchtId = scanner.nextInt();
                    scanner.nextLine();
                    Vlucht personeelVlucht = vluchten.get(personeelVluchtId);
                    Personeel nieuwPersoneelslid = new Personeel(personeelNaam);
                    personeelVlucht.voegPersoneelToe(nieuwPersoneelslid);
                    System.out.println("Personeel toegevoegd!");
                    break;

                case 6:
                    System.out.print("Vlucht ID: ");
                    int printVluchtId = scanner.nextInt();
                    Vlucht printVlucht = vluchten.get(printVluchtId);
                    printVluchtInfo(printVlucht);
                    System.out.println("Vluchtinfo opgeslagen!");
                    break;

                case 7:
                    System.out.println("Programma afgesloten.");
                    return;
                default:
                    System.out.println("Ongeldige keuze, probeer opnieuw.");
            }
        }
    }

    public static void printVluchtInfo(Vlucht vlucht) {
        try (FileWriter writer = new FileWriter(vlucht.getVluchtCode() + "_info.txt")) {
            writer.write("Vluchtcode: " + vlucht.getVluchtCode() + "\n");
            writer.write("Eindbestemming: " + vlucht.getBestemming() + "\n");
            writer.write("Passagiers:\n");
            for (Passagier passagier : vlucht.getPassagiers()) {
                writer.write("- " + passagier.getNaam() + "\n");
            }
            writer.write("Personeel:\n");
            for (Personeel personeel : vlucht.getPersoneelsleden()) {
                writer.write("- " + personeel.getNaam() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Fout bij het opslaan van de vluchtinfo.");
        }
    }
}


// Woensdag 08/01/2025 16:34


// Klasse Passagier
class Passagier {
    private String naam;
    private int leeftijd;
    private String adres;

    public Passagier(String naam, int leeftijd, String adres) {
        this.naam = naam;
        this.leeftijd = leeftijd;
        this.adres = adres;
    }

    public String getNaam() {
        return naam;
    }
}

// Klasse Ticket
class Ticket {
    private Passagier passagier;
    private Vlucht vlucht;
    private String ticketKlasse;

    public Ticket(Passagier passagier, Vlucht vlucht, String ticketKlasse) {
        this.passagier = passagier;
        this.vlucht = vlucht;
        this.ticketKlasse = ticketKlasse;
    }
}

// Klasse Personeel
class Personeel {
    private String naam;

    public Personeel(String naam) {
        this.naam = naam;
    }

    public String getNaam() {
        return naam;
    }
}

// Klasse Vlucht
class Vlucht {
    private String vluchtCode;
    private String bestemming;
    private int economyPlaatsen;
    private int businessPlaatsen;
    private ArrayList<Passagier> passagiers = new ArrayList<>();
    private ArrayList<Personeel> personeelsleden = new ArrayList<>();

    public Vlucht(String vluchtCode, String bestemming, int economyPlaatsen, int businessPlaatsen) {
        this.vluchtCode = vluchtCode;
        this.bestemming = bestemming;
        this.economyPlaatsen = economyPlaatsen;
        this.businessPlaatsen = businessPlaatsen;
    }

    public String getVluchtCode() {
        return vluchtCode;
    }

    public String getBestemming() {
        return bestemming;
    }

    public ArrayList<Passagier> getPassagiers() {
        return passagiers;
    }

    public ArrayList<Personeel> getPersoneelsleden() {
        return personeelsleden;
    }

    public void voegPassagierToe(Passagier passagier) {
        passagiers.add(passagier);
    }

    public void voegPersoneelToe(Personeel personeel) {
        personeelsleden.add(personeel);
    }
}