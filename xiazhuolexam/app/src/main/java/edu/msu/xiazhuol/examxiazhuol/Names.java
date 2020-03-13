package edu.msu.xiazhuol.examxiazhuol;

import java.util.Random;

/**
 * Class the creates a random name and gender.
 */
public class Names {

    /**
     * True if the current choice is a girl.
     */
    private boolean girl = false;

    /**
     * The currently selected name
     */
    private String name = null;

    /**
     * The longest name we will allow. The default is 4.
     */
    private int maxLen = 4;

    private Random random = new Random();

    public Names() {
        choose();
    }

    /**
     * Choose a new random gender and name.
     */
    public final void choose() {
        /*
         * First choose boy or girl
         */
        girl = random.nextBoolean();

        if(girl) {
            chooseFrom(girls, girlsLocs);
        } else {
            chooseFrom(boys, boysLocs);
        }
    }

    /**
     * Choose from the available names and locations
     * @param names Array of names to choose from
     * @param locs Array with the locations of names of a given length
     */
    private void chooseFrom(String [] names, int [] locs) {
        int maxloc;
        if(maxLen > locs.length) {
            maxloc = locs.length;
        } else {
            maxloc = locs[maxLen];
        }

        name = names[random.nextInt(maxloc)];
    }


    /**
     * Get the maximum length name we allow.
     * The default is 4.
     * @return the maximum length
     */
    public int getMaxLen() {
        return maxLen;
    }

    /**
     * Set the maximum length we allow. The default is 4.
     * @param maxLen the maximum to set.
     */
    public void setMaxLen(int maxLen) {
        this.maxLen = maxLen;
    }

    /**
     * Is the current selection a girl name?
     * @return true if selection is a girl name.
     */
    public boolean isGirl() {
        return girl;
    }

    /**
     * Get the currently selected name.
     * @return Current name.
     */
    public String getName() {
        return name;
    }

    /**
     * Locations for the names that start at a given length.
     * The first location is the start for names of length 1.
     * The second location is the start for names of length 2.
     */
    private static final int[] girlsLocs = {0, 0, 1, 58, 289, 712, 1163, 1483, 1624, 1688, 1702};

    /**
     * Collection of names of girls
     */
    private static final String[] girls = {
            "Jo","Ana","Ava","Ida","Ivy","Liv","Mea","Sam","Abi","Ira","Lee","May","Ann","Bhu","Gia","Joy","Pam","Ria","Zia","Aby","Amy","Jan","Mae","Mel","Tia",
            "Dee","Liz","Sue","Una","Bea","Jet","Lia","Mei","Rae","Sky","Ada","Dot","Eve","Ila","Kat","Nia","Uma","Mya","Zoe","Aya","Fay","Jen","Kim","Meg","Xia",
            "Ali","Cat","Eva","Isa","Kay","Lea","Mia","Rue","Aida","Alia","Cate","Cece","Cici","Edna","Elsa","Jada","Kaya","Kora","Leah","Lola","Neve","Olga",
            "Rena","Rosa","Ruth","Sara","Zoya","Amie","Arya","Cami","Cara","Faye","Gigi","Hera","Ilse","Isla","Jaya","Jean","Jena","Joni","Mary","Mina","Nina",
            "Rani","Rose","Suzy","Taya","Teri","Ziva","Aine","Aria","Bess","Enya","Fawn","Isha","Keri","Kira","Lala","Lena","Lila","Lily","Liza","Lora","Lyna",
            "Mika","Naja","Nell","Sage","Suki","Tala","Toni","Vera","Xena","Zali","Bryn","Cher","Dido","Edie","Fern","Irma","June","Kari","Leia","Lesa","Lisa",
            "Lulu","Myra","Nola","Rita","Ruby","Sian","Skye","Zola","Abbi","Alex","Dana","Drew","Elle","Emmy","Ines","Jeri","Joan","Kali","Kiki","Kris","Kyla",
            "Lela","Lyla","Nova","Rene","Thea","Tina","Zuri","Asma","Deja","Dena","Dora","Eden","Eryn","Fran","Gina","Hope","Iris","Jodi","Judy","July","Katy",
            "Lynn","Mada","Mila","Mona","Orla","Sade","Zoey","Alka","Anne","Aqua","Bree","Ciel","Dara","Dawn","Dior","Ella","Fifi","Jade","Jane","Kaye","Lyra",
            "Naya","Niki","Rosy","Shea","Zena","Zula","Abia","Alma","Asha","Asia","Cleo","Demi","Echo","Elly","Emma","Erin","Kara","Kyra","Lara","Lucy","Macy",
            "Maya","Nena","Rhea","Roxy","Star","Tori","Zaya","Abby","Alba","Ally","Anna","Anya","Beth","Bria","Clea","Cora","Dani","Elin","Evie","Flor","Gena",
            "Gwen","Iona","Isis","Izzy","Jess","Kate","Lana","Lexi","Lina","Maia","Mimi","Moya","Nala","Nila","Opal","Zara","Zina","Amya","Ayla","Brea","Cali",
            "Coco","Cori","Dina","Emer","Esme","Gail","Jana","Jill","Jody","Juno","Lacy","Lois","Lori","Luna","Maja","Mara","Mira","Nora","Page","Prue","Roni",
            "Tami","Tara","Tess","Tyra","Veda","Zada","Zora","Alize","Amber","Aniya","April","Becca","Betty","Brynn","Cerys","Daisy","Delia","Farah","Ffion",
            "Gayle","Hilda","Jenny","Kalea","Keira","Leila","Libby","Lorie","Lucia","Magda","Maude","Miley","Nieve","Paige","Pearl","Raina","Rayne","Rhoda",
            "Rosie","Steph","Tamia","Taryn","Tessa","Tracy","Velma","Viola","Aisha","Aleah","Amani","Annie","Ayana","Brisa","Cathy","Clara","Coral","Daria",
            "Deann","Diane","Ebony","Elina","Ellie","Elyza","Emily","Erika","Evita","Flora","Gabby","Hanna","Honor","Jaida","Kerri","Kylie","Laney","Leona",
            "Lexie","Lilah","Linda","Lucky","Maire","Megan","Mercy","Nancy","Nelly","Nicki","Paula","Pippa","Reyna","Rocio","Roxie","Saige","Sammy","Sasha",
            "Selma","Shona","Stacy","Susan","Tonya","Zaria","Abbie","Alexa","Alina","Anais","Audra","Avril","Carys","Cindy","Dolly","Dulce","Ellen","Fleur",
            "Freya","Gemma","Halle","Jadyn","Jemma","Jerri","Joann","Julie","Kacey","Kalia","Katia","Kiley","Kylee","Lorri","Macey","Malia","Mavis","Meera",
            "Milly","Mitzi","Nikki","Oasis","Patti","Penny","Rabia","Renee","Robyn","Rylie","Sofie","Staci","Tammy","Tatum","Zyana","Aggie","Alana","Anisa",
            "Ariel","Aspen","Betsy","Bliss","Briar","Elana","Elise","Emely","Esmay","Heidi","Hetty","Itzel","Jayla","Jewel","Jodie","Jules","Katya","Kiara",
            "Kitty","Lacie","Layla","Liana","Logan","Lynne","Maisy","Margo","Marie","Meryl","Nessa","Paris","Poppy","Raven","Ronda","Salma","Sheri","Sonya",
            "Tanya","Terry","Traci","Trudy","Vicky","Zelda","Aimee","Allie","Anahi","Anuja","Avery","Becky","Beryl","Bunty","Candy","Corra","Dayna","Della",
            "Dixie","Effie","Elena","Estee","Fiona","Freda","Geena","Haley","Holly","Ilene","Jaden","Josie","Karla","Kathy","Kayla","Kelis","Lakyn","Lauri",
            "Leann","Lidia","Lilly","Lucie","Mabel","Marci","Misty","Niamh","Patsy","Quinn","Robin","Rowan","Selah","Sofia","Tegan","Trina","Venus","Wendy",
            "Zaira","Adele","Amari","Anika","Aoife","Britt","Caris","Celia","Chris","Clare","Ember","Faith","Janie","Jenni","Karen","Kasey","Kiana","Kiswa",
            "Lacey","Leena","Leora","Lizzy","Luisa","Mandy","Marla","Naomi","Odele","Paola","Polly","Regan","Rhian","Riley","Sally","Sandy","Seren","Shawn",
            "Shyla","Sonja","Trixy","Vonda","Zayla","Abbey","Alice","Beccy","Bobbi","Carly","Debra","Elsie","Emmie","Helga","Jayne","Laila","Lesly","Livia",
            "Lorna","Lydia","Maddy","Maria","Maura","Nadia","Norma","Olive","Peggy","Raine","Rhona","Rylee","Sarah","Shana","Sophy","Tammi","Tasha","Tilly",
            "Wendi","Zahra","Amara","Amina","Andie","Angie","Belle","Carol","Chloe","Darla","Donna","Elisa","Ethel","Grace","Imani","Irene","Jamie","Jayda",
            "Jenna","Karyn","Kelly","Kerry","Leela","Lexis","Loren","Lotus","Mindy","Molly","Nicky","Pansy","Petra","Pixie","Priya","Rania","Reese","Rikki",
            "Saira","Sonia","Sybil","Tania","Tayla","Terri","Vicki","Wilma","Addie","Alisa","Bella","Casey","Darcy","Deana","Deena","Diana","Eabha","Erica",
            "Ester","Frida","Hazel","Honey","Ilona","Janet","Jolie","Joyce","Karly","Katie","Kelli","Lilac","Loran","Lynda","Macie","Marcy","Moira","Ocean",
            "Patty","Perla","Piper","Randi","Shari","Suzie","Talia","Tamra","Tonia","Trish","Agnes","Alena","Amaya","Angel","Anita","Apple","Carla","Cayla",
            "Cheri","Ciara","Denny","Doris","Edith","Eliza","Elora","Fanny","Ginny","Greta","Helen","India","Ivana","Janis","Jorja","Julia","Karin","Keely",
            "Kiera","Kimmy","Kizzy","Laura","Leigh","Luann","Maeve","Myrna","Rayna","Reina","River","Sadie","Susie","Tiana","Wanda","Acacia","Alanna","Alyson",
            "Angela","Ashley","Athena","Azalea","Blythe","Brogan","Callie","Carley","Cherie","Corina","Daphne","Davina","Dionne","Hailey","Helena","Indigo",
            "Isabel","Jancis","Janiya","Joelle","Karina","Kassie","Kendra","Kierra","Kinley","Kristy","Laurel","Leslie","Lupita","Lynsey","Marina","Marley",
            "Melina","Nadene","Noreen","Roanne","Shayla","Sherri","Sienna","Skyler","Sophie","Sutton","Teagan","Ulrica","Winter","Yazmin","Zelina","Alisha",
            "Aoibhe","Ariana","Barbie","Bethan","Billie","Briana","Carmen","Charis","Chiara","Danica","Deidre","Eimear","Esther","Gloria","Joanne","Kailey",
            "Kamryn","Katlyn","Kayley","Kellie","Kristi","Lavana","Lottie","Mollie","Muriel","Rachel","Sabine","Sharon","Shelia","Sinead","Sondra","Tamsin",
            "Trisha","Vivien","Willow","Ximena","Adalyn","Alayna","Amalia","Anusha","Arlene","Bailey","Bonita","Brenna","Carmel","Cecily","Darcie","Daysha",
            "Deedee","Eileen","Eliana","Elvira","Emilia","Evelyn","Gladys","Hayley","Iliana","Ingrid","Jasmin","Jazmin","Jolene","Karlee","Kaylee","Kelley",
            "Krista","Laurie","Leanna","Lettie","Maggie","Marcia","Mariam","Martha","Melody","Oonagh","Ramsha","Reanne","Rowena","Samira","Sariya","Selena",
            "Sheila","Sheryl","Simone","Thelma","Ursula","Vivian","Adelia","Alexus","Alivia","Alycia","Amaris","Ashlee","Aurora","Ayisha","Benthe","Cassie",
            "Celina","Clover","Dagmar","Dashee","Debbie","Denise","Doreen","Eloise","Eunice","Fallon","Ginger","Hadley","Imogen","Janine","Jeanne","Jennie",
            "Jordyn","Keeley","Kelsie","Latoya","Lilita","Louise","Marlee","Minnie","Monica","Nicole","Odelia","Peyton","Raegan","Regina","Sadhbh","Serena",
            "Shawna","Sylvia","Tamera","Tayler","Wallis","Yasmin","Yvonne","Adalia","Alayah","Alicia","Amelie","Anjali","Anneke","Arleen","Ashlyn","Aubrey",
            "Bobbie","Brenda","Carter","Cheryl","Cierra","Connie","Darcey","Emilee","Evelin","Fatima","Haylee","Helina","Jazlyn","Jemima","Joleen","Katara",
            "Kenzie","London","Mariah","Marisa","Marsha","Meagan","Millie","Morgan","Nadine","Nayeli","Nikita","Olivia","Paloma","Ramona","Reanna","Rhonda",
            "Safiya","Samara","Sarina","Shania","Sheena","Sherry","Silvia","Stacey","Tahlia","Tammie","Agatha","Alaina","Alexis","Alissa","Amirah","Andrea",
            "Ayesha","Benita","Brandy","Carole","Deanne","Dianne","Elaine","Elodie","Gracie","Habiba","Harley","Heaven","Hester","Imelda","Isolde","Jackie",
            "Jamiya","Jayden","Kaylin","Kelsey","Lorena","Louisa","Maisie","Margie","Merida","Nicola","Noelle","Raquel","Shelly","Starla","Sydney","Tamara",
            "Taylah","Terrie","Tracey","Vickie","Winnie","Yvette","Aileen","Alyssa","Amelia","Anabel","Aniyah","Arisha","Ashlie","Aubree","Avalon","Azaria",
            "Bertha","Bridie","Bronte","Bryony","Carrie","Chanel","Cherry","Dakota","Edwina","Farrah","Gaynor","Hattie","Hollie","Indira","Iyanna","Jensen",
            "Keisha","Kinsey","Lauren","Lillie","Lolita","Marion","Marnie","Meadow","Miriam","Phoebe","Quiana","Reagan","Renata","Safire","Sierra","Sloane",
            "Teegan","Thalia","Tricia","Violet","Xanthe","Yelena","Aerona","Alecia","Alexia","Alison","Annika","Ayanna","Baylee","Blaire","Brandi","Camila",
            "Carina","Cassia","Catlin","Deanna","Dianna","Elaina","Hannah","Honour","Isobel","Janice","Jessie","Jordan","Judith","Kailyn","Kaylie","Kirsty",
            "Leonie","Lilian","Lizzie","Malory","Maryam","Meghan","Nishka","Odalis","Roisin","Sandra","Saskia","Senuri","Shauna","Shreya","Trixie","Xochil",
            "Yvaine","Amanda","Ashton","Audrey","Bianca","Bonnie","Briony","Brooke","Cailin","Claire","Eilidh","Ellery","Elysia","Emilie","Gianna","Glenda",
            "Hallie","Joanna","Juliet","Kamala","Kerian","Lauryn","Leanne","Lenore","Lorrie","Marian","Maxine","Nellie","Pamela","Rianna","Sabina","Sahara",
            "Sammie","Sascha","Selina","Shelby","Simran","Stacie","Summer","Tawana","Teresa","Verity","Ysabel","Alanis","Aliyah","Anthea","Astrid","Autumn",
            "Camryn","Celine","Dahlia","Danika","Davida","Debora","Hailee","Harper","Hilary","Ianthe","Lesley","Lianne","Lilith","Maddie","Margot","Milena",
            "Monika","Nevaeh","Odette","Payton","Portia","Raelyn","Salome","Sidney","Skylar","Sophia","Stella","Sylvie","Taylor","Tracie","Winona","Zelida",
            "Aarushi","Adeline","Arielle","Bernice","Breanna","Candace","Carolyn","Charlie","Colette","Deborah","Desiree","Dorothy","Electra","Emerald","Harriet",
            "Jaylynn","Jeannie","Jillian","Joselyn","Juliana","Justice","Ladonna","Lillian","Makayla","Marcela","Margret","Miranda","Monique","Natasha","Patrice",
            "Queenie","Saffron","Shakira","Tabatha","Tiffany","Zaheera","Addison","Allyson","Annabel","Ashvini","Audrina","Braelyn","Caitlan","Chelsea","Genesis",
            "Jacinda","Janette","Jessica","Juanita","Katelyn","Kirstin","Lizette","Lorelei","Lyndsey","Madison","Mallory","Mariana","Maritza","Mikayla","Octavia",
            "Ophelia","Pandora","Rebekah","Rihanna","Taliyah","Theresa","Yulissa","Allison","America","Ashlynn","Belinda","Brielle","Bronwyn","Cadence","Calypso",
            "Caoimhe","Chantal","Cosette","Danette","Delores","Diamond","Estelle","Honesty","Izidora","Janessa","Lucille","Marisol","Morgana","Nichola","Phyllis",
            "Shannon","Suzette","Tenille","Trinity","Whitney","Yolanda","Anselma","Braylee","Caleigh","Camille","Carissa","Chrissy","Clarice","Delaney","Emmalyn",
            "Frances","Georgia","Grainne","Harmony","Kaitlyn","Loretta","Madalyn","Manuela","Mckenna","Mildred","Natalia","Orlaith","Pauline","Rhianna","Saoirse",
            "Susanne","Vanessa","Adriana","Alyssia","Azariah","Beyonce","Briella","Bronwen","Carlynn","Cecilia","Christi","Claudia","Destiny","Elspeth","Janelle",
            "Justine","Kinsley","Leticia","Maureen","Melissa","Phoenix","Preslie","Ryleigh","Suzanne","Tatiana","Xaviera","Yesenia","Zendaya","Abigail","Aleisha",
            "Alondra","Arianne","Ashanti","Aurelia","Bethany","Blanche","Brianne","Bryanna","Caitlyn","Camilla","Cassidy","Catrina","Charity","Christy","Clodagh",
            "Cynthia","Darlene","Eugenie","Jeanine","Jordana","Kaitlin","Kristin","Larissa","Lavinia","Liliana","Lindsey","Madyson","Maryann","Mckayla","Minerva",
            "Myfanwy","Nerissa","Paulina","Petunia","Promise","Rolanda","Rozlynn","Savanna","Tanisha","Valerie","Xochitl","Alannah","Alessia","Angelia","Antonia",
            "Beverly","Candice","Cecelia","Colleen","Corinne","Delilah","Elektra","Emerson","Frankie","Giselle","Haleigh","January","Johanna","Justina","Kaleigh",
            "Karissa","Kathryn","Kennedy","Krystal","Leandra","Leilani","Liberty","Luciana","Makenna","Melinda","Michele","Montana","Paisley","Presley","Roberta",
            "Rosalie","Sherrie","Suzanna","Tabitha","Venetia","Aisling","Arianna","Brianna","Britney","Caitlin","Chelsey","Coralie","Crystal","Daniela","Deirdre",
            "Felicia","Gillian","Gwyneth","Heather","Jacinta","Jocelyn","Julissa","Katrina","Kristie","Lindsay","Lynette","Madisyn","Mairead","Nanette","Orianna",
            "Rosella","Sabrina","Shelley","Siobhan","Susanna","Therese","Valeria","Ainsley","Annette","Ariadne","Artemis","Barbara","Cameron","Caprice","Carmela",
            "Celeste","Dolores","Hepsiba","Jaelynn","Jasmine","Jazmine","Kirsten","Kristen","Kyleigh","Lizbeth","Lucinda","Madelyn","Malinda","Marissa","Martina",
            "Mikaela","Nichole","Rachael","Rebecca","Rosanna","Roxanne","Shirley","Solange","Valarie","Viviana","Aaliyah","Adelina","Ariella","Beatrix","Bettina",
            "Blossom","Bridget","Brynlee","Charley","Christa","Delanie","Eleanor","Georgie","Jaylinn","Kassidy","Kendall","Lisette","Marilyn","Marlene","Matilda",
            "Melanie","Natalie","Rosetta","Scarlet","Yasmine","Annalise","Clarisse","Emmeline","Francine","Georgina","Giovanna","Gretchen","Kathleen","Maddison",
            "Michaela","Precious","Scarlett","Waverley","Adrienne","Brooklyn","Celestia","Cristina","Demetria","Hazeline","Juliette","Karolina","Margaret",
            "Veronica","Adrianna","Angelina","Cheyanne","Claudine","Katarina","Madeline","Primrose","Rosalynn","Annabeth","Beatrice","Brittany","Carolina",
            "Catriona","Charlene","Chrystal","Cordelia","Danielle","Hyacinth","Kristina","Marybeth","Prudence","Rosemary","Savannah","Stefanie","Angelica",
            "Arabella","Callista","Chanelle","Gertrude","Gisselle","Isabelle","Izabella","Julianne","Lorraine","Makenzie","Michelle","Penelope","Rosalind",
            "Virginia","Annmarie","Chenille","Coraline","Daniella","Emmaline","Felicity","Katerina","Kimberly","Marianne","Susannah","Adreanna","Calliope",
            "Caterina","Charlize","Christal","Destinee","Griselda","Isabella","Jeannine","Julianna","Kiersten","Kourtney","Marcella","Patricia","Samantha",
            "Adelaide","Bethanie","Charissa","Emanuela","Fabrizia","Florence","Gabriela","Hermione","Jeanette","Katelynn","Lavender","Marianna","Marjorie",
            "Meredith","Mikhaela","Paulette","Rachelle","Tallulah","Vivienne","Adrianne","Amethyst","Anamaria","Angeline","Cathleen","Chastity","Cheyenne",
            "Christie","Courtney","Delphine","Kaidence","Kayleigh","Mercedes","Princess","Renesmee","Rochelle","Theodora","Annalisa","Ashleigh","Brittney",
            "Caroline","Clarissa","Fernanda","Jennifer","Kaitlynn","Kristine","Lynnette","Mckenzie","Patience","Phillipa","Rhiannon","Sapphire","Serenity",
            "Sheridan","Victoria","Bridgette","Elizabeth","Esmeralda","Philomena","Anastasia","Angelique","Bellatrix","Cassandra","Christina","Dominique",
            "Gwendolyn","Kimberlee","Mackenzie","Priscilla","Valentina","Annemarie","Catherine","Charmaine","Constance","Guinevere","Henrietta","Alejandra",
            "Andromeda","Aphrodite","Gabrielle","Kassandra","Katharine","Winnifred","Cathalina","Charlotte","Guadalupe","Jaqueline","Madeleine","Magdalene",
            "Annabelle","Elisabeth","Gabriella","Georgette","Rosemarie","Thomasina","Claudette","Esperanza","Geraldine","Josephine","Magdalena","Millicent",
            "Stephanie","Annabella","Aoibheann","Augustina","Brooklynn","Celestine","Christine","Genevieve","Kimberley","Margarita","Alexandra","Chantelle",
            "Francesca","Jacquelyn","Jeannette","Katherine","Nicolette","Shawnette","Alessandra","Antoinette","Christabel","Evangeline","Alexandria","Clementine",
            "Persephone","Christiana","Jacqueline","Kimbriella","Clarabelle","Wilhelmina","Bernadette","Emmanuelle","Sebastianne","Alyshialynn"
    };

    /**
     * Locations for the names that start at a given length.
     * The first location is the start for names of length 1.
     * The second location is the start for names of length 2.
     */
    private static final int[] boysLocs = {0, 0, 3, 81, 304, 659, 1029, 1228, 1304, 1328, 1331};

    /**
     * Collection of names of boys
     */
    public static final String[] boys = {
            "Ed","Ty","Al","Ade","Ash","Cal","Jed","Jim","Mat","Ned","Oli","Raj","Rio","Tod","Dev","Han","Jon","Ken","Kit","Pat","Zeb","Eli","Huw","Lee","Lex",
            "Ram","Rob","Asa","Bob","Ira","Kim","Tam","Zac","Ben","Guy","Jay","Kai","Seb","Tex","Tim","Tom","Ari","Cam","Dan","Don","Kye","Moe","Ray","Rik",
            "Zed","Fox","Kip","Noe","Rex","Rod","Ace","Bay","Cai","Dax","Ian","Joe","Les","Mel","Teo","Art","Ike","Ron","Roy","Sam","Ted","Van","Ali","Che",
            "Gus","Jai","Jax","Jeb","Leo","Max","Sid","Zak","Alen","Amit","Cruz","Dale","Dino","Eddy","Eoin","Ezra","Fred","Gale","Gian","Greg","Hugo","Jack",
            "John","Juan","Kade","Kash","Kent","Leon","Lyle","Mark","Noah","Reed","Rory","Troy","Wade","Zion","Axel","Bert","Burt","Clay","Coby","Earl","Evan",
            "Gino","Iain","Jody","Kian","Kurt","Lake","Lars","Lief","Nath","Oran","Ozzy","Raul","Rhys","Rico","Theo","Tito","Will","Yuri","Bart","Boyd","Buck",
            "Cael","Carl","Chip","Colt","Dara","Erik","Igor","Judd","Marc","Matt","Mick","Olly","Owen","Reza","Stan","Thad","Zack","Zayn","Adan","Alec","Beau",
            "Cain","Chaz","Cian","Dana","Duke","Emet","Enzo","Ewan","Gael","Glen","Ivor","Jago","Joey","Karl","Kobe","Luke","Nile","Phil","Remy","Rich","Umar",
            "Zeke","Abel","Alex","Colm","Curt","Dirk","Doug","Egon","Elmo","Euan","Jake","Jeff","Lane","Lynn","Nash","Nico","Noel","Otto","Roan","Ross","Shay",
            "Tate","Adam","Aldo","Hans","Ivan","Joel","Kane","Klay","Link","Paco","Paul","Yves","Zain","Aden","Alan","Amos","Bram","Cade","Cash","Chad","Dave",
            "Dean","Dion","Eden","Egan","Eric","Gene","Iggy","Kris","Levi","Luca","Milo","Neil","Otis","Reef","Rick","Said","Sean","Todd","Zane","Andy","Cato",
            "Cody","Cory","Drew","Gabe","Hank","Josh","Luis","Mike","Mitt","Ravi","Reid","Rian","Scot","Seth","Tony","Trey","Aran","Asif","Brad","Bret","Dick",
            "Finn","Jase","Jose","Jude","Kale","Kirk","Liam","Mack","Nate","Omar","Pete","Ryan","Saul","Shea","Sven","York","Amir","Arlo","Asad","Beck","Bill",
            "Cary","Cole","Dane","Dash","Emil","Gage","Gary","Hugh","Jace","Jett","Kody","Kyle","Neal","Nick","Nils","Olaf","Rene","Rudy","Toby","Zach","Abdul",
            "Alain","Arman","Billy","Bodie","Brock","Cadby","Casey","Cesar","Colin","Dewey","Efren","Ellis","Ethan","Fidel","Gavin","Ieuan","Irwin","Jared",
            "Jaxon","Jonty","Keith","Kolby","Kylen","Layne","Leven","Malik","Micah","Myles","Pedro","Randy","Rocky","Rufus","Sacha","Sandy","Shaun","Spike",
            "Tariq","Tracy","Uriel","Vihan","Yahir","Ahmed","Albie","Alfie","Aspen","Basil","Benji","Brett","Bruce","Calum","Conan","Cyril","Darcy","Diego",
            "Drake","Ennio","Fionn","Frank","Fritz","Grady","Imran","Jamal","Jason","Jesse","Kaleb","Keanu","Leroy","Loren","Luigi","Marco","Mekhi","Oakes",
            "Peter","Ramon","Rehan","Robby","Ryder","Samir","Slade","Teddy","Tommy","Vance","Woody","Arrie","Brent","Clark","Clive","Corey","Dylan","Elton",
            "Emmet","Homer","Isiah","Jacob","Jakob","Jorge","Kelly","Kirby","Lacey","Logan","Mehdi","Misha","Monty","Nasir","Nolan","Petar","Romeo","Rowan",
            "Rusty","Sasha","Timmy","Tomas","Tyler","Vince","Zylen","Amari","Arjun","Bevan","Brice","Butch","Cecil","Cliff","Colby","Conor","Daire","Darin",
            "Daryl","Devon","Edwin","Ervin","Flynn","Garth","Grant","Henri","Jamie","Javon","Kylar","Lance","Lenny","Linus","Louie","Mario","Milan","Oisin",
            "Oscar","Paddy","Rahul","Riker","Robin","Roger","Ronny","Shane","Soren","Tobin","Trace","Zaine","Adnan","Allen","Andre","Barry","Blain","Brody",
            "Caden","Carey","Chevy","Dante","David","Edgar","Erick","Fabio","Geoff","Harry","Jimmy","Jordy","Judas","Kevin","Kiran","Krish","Kyrin","Lewis",
            "Lucas","Monte","Ollie","Perry","Quinn","Ralph","Reese","Roman","Simon","Stacy","Trent","Wiley","Aiden","Alvin","Angel","Arwin","Ayden","Blaze",
            "Brian","Bryan","Chase","Chuck","Cohen","Craig","Dacey","Denis","Duane","Emery","Errol","Felix","Floyd","Garry","Ianto","Inigo","Jadon","Jesus",
            "Jonas","Loris","Lukas","Mikey","Niall","Remco","Rubin","Rylan","Scott","Sonny","Talon","Toryn","Allan","Anton","Asher","Boris","Bryon","Caleb",
            "Clint","Danny","Derek","Elmer","Galen","Gregg","Isaac","Jaime","Judah","Kaden","Kerry","Kyler","Lloyd","Manny","Mateo","Myron","Nicky","Paolo",
            "Percy","River","Shawn","Silas","Terry","Tiger","Usama","Vijay","Aidan","Alden","Alton","Aston","Ayaan","Benjy","Blake","Bruno","Cyrus","Damon",
            "Daren","Devin","Eliot","Enoch","Ernie","Heath","Issac","Jaden","James","Jonah","Julio","Klaus","Lamar","Marty","Moses","Nevin","Orion","Pablo",
            "Rider","Ronan","Ruben","Ryker","Sammy","Tadhg","Titus","Tyson","Vasco","Wayne","Wyatt","Yusuf","Abram","Ahmad","Avery","Blair","Buddy","Chris",
            "Clyde","Davis","Doyle","Eamon","Elias","Elvis","Giles","Hywel","Jalen","Jerry","Larry","Mitch","Nigel","Ozzie","Piers","Rhett","Ricky","Zayne",
            "Aaron","Angus","Benny","Bobby","Brady","Bryce","Byron","Caius","Eddie","Gerry","Glenn","Henry","Idris","Johan","Josue","Karla","Kenny","Leuan",
            "Louis","Mason","Miles","Reece","Riley","Rocco","Rohan","Ryley","Steve","Tiago","Uriah","Ziggy","August","Benson","Brydon","Daniel","Deacon","Denzel",
            "Dustin","Foster","Gustav","Harris","Jaiden","Jeremy","Landon","Lowell","Miller","Oswald","Palmer","Quincy","Reuben","Richie","Roland","Seamus",
            "Sidney","Steven","Willie","Zander","Abriel","Andrew","Archer","Arthur","Blaise","Braden","Caesar","Cathal","Damion","Darrin","Dawson","Elijah",
            "Febian","Hayden","Israel","Jaylen","Jeffry","Joseph","Julian","Leland","Maddox","Martin","Miguel","Morris","Porter","Rafael","Ronald","Ruairi",
            "Sawyer","Sergio","Stefan","Trevor","Tyrone","Vinnie","Waylon","Adrian","Albert","Alonzo","Antony","Ashton","Austin","Blaine","Brogan","Callan",
            "Curtis","Damian","Davion","Declan","Django","Edison","Eugene","Faisal","Finley","Gareth","Godwin","Haiden","Harvey","Jarvis","Jayden","Jerome",
            "Kaiden","Kaylen","Khalid","Leonel","Lucian","Nesbit","Pascal","Pierce","Rashan","Reggie","Rickie","Rodger","Shayne","Skylar","Stuart","Taylor",
            "Walter","Yestin","Angelo","Aubrey","Bailey","Bennie","Bryant","Camden","Carter","Daxton","Dennis","Donald","Elliot","Fergus","Gunner","Harley",
            "Hudson","Jayson","Jerald","Jethro","Josiah","Keegan","Lester","Marvin","Merick","Murphy","Paxton","Prince","Ryland","Samuel","Shiloh","Thomas",
            "Vernon","Vivian","Wesley","Willem","Xavier","Arnold","Ashley","Austen","Boston","Bryson","Casper","Chance","Corbin","Dalton","Darrel","Dwight",
            "Finlay","Freddy","Gannon","Godric","Gregor","Holden","Hunter","Isaiah","Jarrod","Kayden","Kellin","Manuel","Marlon","Mathew","Milton","Nelson",
            "Parker","Rashad","Rickey","Vikram","Wilson","Yehudi","Zayden","Zuriel","Alfred","Baylor","Buster","Caiden","Carson","Cayden","Connor","Darian",
            "Darwin","Daxter","Devlin","Dillon","Easton","Elisha","Gerard","Graham","Gunnar","Hector","Hubert","Javier","Jaylon","Jenson","Joshua","Julius",
            "Lamont","Laurie","Lennox","Leslie","Marcus","Martyn","Melvin","Raheem","Randal","Ronnie","Samson","Tobias","Vaughn","Vishal","Xander","Barney",
            "Bladen","Brodie","Camron","Clancy","Collin","Cooper","Cullen","Dallas","Dexter","Dorian","Dwayne","Emilio","Fabian","Finbar","Gideon","Herman",
            "Jackie","Jaxson","Jediah","Jimmie","Jordan","Kasper","Kellen","Landyn","Layton","Lyndon","Marley","Moises","Oliver","Rajesh","Roscoe","Rupert",
            "Sanjay","Stacey","Stevie","Travis","Tucker","Walker","Wilbur","Willis","Zoltan","Archie","Arturo","Bertie","Brayan","Brevyn","Burton","Calvin",
            "Carsen","Conner","Darryl","Dieter","Edward","Felipe","Gerald","Graeme","Howard","Jasper","Jaylin","Jensen","Jessie","Keaton","Kieran","Kurtis",
            "Lennie","Magnus","Marcos","Nathan","Oakley","Peyton","Ramsey","Robert","Virgil","Willam","Zaiden","Alexis","Andres","Bernie","Caelan","Callum",
            "Carlos","Claude","Colton","Cormac","Damien","Daragh","Darren","Deepak","Dmitri","Edmund","Emmett","Ernest","Farley","Garman","George","Gordon",
            "Hamish","Hassan","Horace","Ismael","Jacoby","Jaydon","Johnny","Kayson","Kelvin","Khalil","Lonnie","Marcel","Mehtab","Mickey","Morgan","Nestor",
            "Norman","Rameel","Rashid","Robbie","Rodney","Skyler","Tommie","Trevon","Tyrese","Warren","Audwin","Cedric","Ciaran","Conrad","Dakota","Darius",
            "Dayton","Denver","Dinesh","Donnie","Duncan","Efrain","Eoghan","Fraser","Harold","Irving","Justin","Keenan","Landen","Lawson","Lionel","Luther",
            "Marion","Mervyn","Murray","Payton","Philip","Raiden","Scotty","Tanner","Tracey","Victor","Weston","Xerxes","Anthony","Barnaby","Beckett","Braeden",
            "Brenden","Chesney","Cillian","Clifton","Darnell","Dashawn","Donovan","Glyndwr","Herbert","Phillip","Wilbert","Braxton","Carlton","Charles","Delbert",
            "Dominic","Eduardo","Emanuel","Ernesto","Garrett","Geraint","Griffin","Horatio","Jacques","Neville","Niklaus","Rodolfo","Sheldon","Zackery","Abraham",
            "Andreas","Brandon","Cassius","Darrell","Deshawn","Douglas","Francis","Gregory","Jeffery","Joachim","Johnnie","Langdon","Marquis","Nicolas","Quinton",
            "Trenton","Wilfred","Winston","Ainsley","Alfredo","Bradwin","Braydon","Dimitri","Gerardo","Ibrahim","Justice","Kenneth","Malakai","Mohamed","Randall",
            "Raymond","Rudolph","Scottie","Tristen","Antonio","Bernard","Branden","Brennan","Calhoun","Clinton","Deandre","Derrick","Dhalsim","Gilbert","Ignacio",
            "Jackson","Leandro","Lochlan","Maxwell","Micheal","Phoenix","Russell","Stewart","Wallace","Zachery","Atticus","Bennett","Bertram","Bradley","Brayden",
            "Clement","Edwardo","Enrique","Everett","Gabriel","Jameson","Kedrick","Killian","Malachi","Nikolas","Octavio","Orlando","Phebian","Preston","Ricardo",
            "Roberto","Rogelio","Sherman","Stephen","Terence","Tristan","Ulysses","Wendell","Willard","Armando","Bellamy","Bentley","Brendon","Chester","Darragh",
            "Etienne","Freddie","Godfrey","Gustavo","Jarrett","Jericho","Leonard","Maximus","Michael","Phineas","Quinlan","Raphael","Rolando","Tarquin","Yardley",
            "Zachary","Alfonso","Charlie","Clayton","Earnest","Emerson","Frankie","Giorgio","Indiana","Kameron","Kendall","Lincoln","Lorenzo","Maurice","Nikolai",
            "Patrick","Prakash","Rodrigo","Solomon","Stephan","Zebulon","Alberto","Bronson","Castiel","Desmond","Eustace","Greyson","Jeffrey","Joaquin","Lachlan",
            "Leopold","Luciano","Matthew","Stanley","Timothy","Vincent","Wilhelm","Zackary","Barclay","Brendan","Brinley","Cameron","Dewayne","Elliott","Esteban",
            "Ezekiel","Forrest","Grayson","Jamison","Malcolm","Osvaldo","Padraig","Quentin","Richard","Sandeep","Shannon","Spencer","Terrell","Triston","William",
            "Alistair","Campbell","Emiliano","Kingston","Lisandro","Mohammed","Nickolas","Terrence","Tiberius","Alphonso","Matthias","Mitchell","Benedict",
            "Bernardo","Chandler","Diarmuid","Geoffrey","Gilberto","Kristian","Lysander","Cristian","Franklyn","Jonathan","Lawrence","Maverick","Nicholas",
            "Sterling","Terrance","Alastair","Clarence","Emmanuel","Jermaine","Leonardo","Reynaldo","Roderick","Salvador","Santiago","Valentin","Franklin",
            "Giovanni","Kendrick","Mauricio","Muhammad","Shadrach","Abdullah","Augustus","Harrison","Humphrey","Bradford","Dominick","Fletcher","Garrison",
            "Laurence","Rafferty","Sherlock","Theodore","Aloysius","Benjamin","Brantley","Channing","Cuthbert","Fredrick","Leighton","Marshall","Pasquale",
            "Reginald","Thaddeus","Wolfgang","Clifford","Fernando","Jeremiah","Jonathon","Kingsley","Mohammad","Randolph","Vladimir","Zachariah","Christian",
            "Cornelius","Sylvester","Alexander","Frederick","Salvatore","Sebastian","Valentino","Nathaniel","Francesco","Johnathon","Nathanael","Alesandro",
            "Johnathan","Demetrius","Evangelos","Guillermo","Francisco","Quintrell","Sebestian","Alejandro","Cristobal","Roosevelt","Maximilian","Theophilus",
            "Kristopher","Constantine","Christopher","Bartholomew"
    };
}
