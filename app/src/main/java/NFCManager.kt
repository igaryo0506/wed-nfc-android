import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import java.nio.charset.Charset

object NFCManager {

    private var isWriting = false
    private lateinit var ndefMessage: NdefMessage
    private var writeHandler: (() -> Unit)? = null
    private var readHandler: ((String?) -> Unit)? = null

    fun write(text: String, handler: (() -> Unit)? = null) {
        writeHandler = handler
        isWriting = true
        val textRecord = NdefRecord.createTextRecord(null, text)
        ndefMessage = NdefMessage(arrayOf(textRecord))
    }

    fun read(handler: (String?) -> Unit) {
        readHandler = handler
        isWriting = false
    }

    fun handleTag(tag: Tag) {
        if (isWriting) {
            writeTag(tag)
        } else {
            readTag(tag)
        }
    }

    private fun writeTag(tag: Tag) {
        val ndef = Ndef.get(tag)
        ndef.connect()
        ndef.writeNdefMessage(ndefMessage)
        ndef.close()
        writeHandler?.invoke()
    }

    private fun readTag(tag: Tag) {
        val ndef = Ndef.get(tag) ?: return
        ndef.connect()
        val ndefMessage = ndef.ndefMessage ?: return
        val record = ndefMessage.records.firstOrNull() ?: return
        val payload = record.payload
        val textEncoding = if ((payload[0].toInt() and 128) == 0) "UTF-8" else "UTF-16"
        val languageCodeLength = payload[0].toInt() and 63
        val text = String(payload, languageCodeLength + 1, payload.size - languageCodeLength - 1, Charset.forName(textEncoding))
        ndef.close()
        readHandler?.invoke(text)
    }
}
