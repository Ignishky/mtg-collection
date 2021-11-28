package fr.ignishky.mtgcollection.common;

import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.domain.set.SetName;
import fr.ignishky.mtgcollection.domain.set.event.SetAdded;

import static fr.ignishky.mtgcollection.domain.set.SetId.toSetId;

public class DomainFixtures {

    public static Set aSet = new Set(toSetId("a46f2063-5607-43aa-9ec6-c366c1afa02f"), new SetCode("code1"), new SetName("name1"), "2011-09-12");
    public static Set aSavedSet = new Set(aSet.id(), aSet.code(), aSet.name(), null);
    public static Set anotherSet = new Set(toSetId("d95beffd-f2a2-4e31-8888-547db49cc3bf"), new SetCode("code2"), new SetName("name2"), "2018-10-12");
    public static Set anotherSavedSet = new Set(anotherSet.id(), anotherSet.code(), anotherSet.name(), null);
    public static Set aFutureSet = new Set(toSetId("7d082e55-c58f-425f-b561-d25096089e58"), new SetCode("wtf"), new SetName("NON-EXISTING SETS"), "9999-12-31");

}
