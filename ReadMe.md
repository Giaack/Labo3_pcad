I Thread agiscono tutti sulla stessa lista di Eventi, per costruzione abbiamo deciso che :
    - se un evento non esiste ancora, verrà mostrato a schermo che non esiste, e l'utente deciderà se aspettare che l'evento verrà creato, oppure terminare manualmente il programma
    - Il Thread che sta cercando di prenotare un evento con x posti, ma i posti disponibili, al momento della prenotazione, sono y < x, allora il thread rimane in attesa, controllando che i posti vengano aumentati. (anche qui, se l'utente non vuole che il thread rimanga in attesa deve terminare manualmente il programma)
    (ovviamente questa è stata una scelta di implementazione, avremmo potuto far diversaemnte, per esempio mettere in attesa per 10 secondi il thread, ed una volta passati, avrebbe fatto un ulteriore controllo e sarebbe terminato se il controllo non fosse stato passato) 

Nell' esempio che mostriamo infatti, facciamo prenotare gli eventi 'nome' ed 'evento1' (che viene prima eliminato e poi ricreato), ben prima che questi vengano creati, e vediamo che i thread rimangono ad aspettare che il thread venga creato e solo in seguito lo prenotano.
Vediamo che ciò funziona sia quando: 
    - l'evento viene eliminato ('evento1') 
    - l'evento non è mai esistito (evento 'nome') 
    - i posti vengono aggiunti dopo che il thread abbia cercato di prenotare ('evento4') 