package com.gebeya.smartcontract.view.publicLedger;


/**
 * Interface for the click callback for a single PublicLedger item
 * inside a RecyclerView.
 */

public interface PublicLedgerCallback {
    void onSelected(int position, String id);
}
